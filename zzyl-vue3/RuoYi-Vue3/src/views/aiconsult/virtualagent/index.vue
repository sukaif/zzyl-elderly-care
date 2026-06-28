<template>
  <div class="app-container virtual-agent">
    <div class="va-layout">
      <div class="va-left">
        <div class="va-left-header">
          <el-button type="primary" class="va-new" :icon="Plus" @click="handleNewSession">
            新增对话
          </el-button>
        </div>

        <div class="va-left-section-title">历史记录</div>
        <el-scrollbar class="va-session-list">
          <div
            v-for="s in sessions"
            :key="s.id"
            class="va-session-item"
            :class="{ active: s.id === activeSessionId }"
            @click="activeSessionId = s.id"
          >
            <div class="va-session-item-main">
              <el-icon class="va-session-icon"><ChatDotRound /></el-icon>
              <div class="va-session-title" :title="s.title">{{ s.title }}</div>
            </div>
            <el-button
              text
              type="danger"
              size="small"
              class="va-session-del"
              :icon="Delete"
              @click.stop="handleDeleteSession(s.id)"
            />
          </div>
        </el-scrollbar>
      </div>

      <div class="va-right">
        <div class="va-chat">
          <el-scrollbar ref="chatScrollRef" class="va-chat-scroll">
            <div class="va-messages">
              <div
                v-for="m in activeMessages"
                :key="m.id"
                class="va-msg"
                :class="m.role"
              >
                <div class="va-msg-avatar">
                  <el-icon v-if="m.role === 'assistant'"><Cpu /></el-icon>
                  <el-icon v-else><User /></el-icon>
                </div>

                <div class="va-msg-bubble">
                  <div v-if="m.role === 'assistant' && m.content === '' && sending" class="va-msg-loading">
                    <span class="dot"></span>
                    <span class="dot"></span>
                    <span class="dot"></span>
                  </div>
                  <div v-else class="va-msg-text" v-text="m.content" />
                </div>
              </div>
            </div>
          </el-scrollbar>

          <div class="va-input">
            <el-input
              v-model="draft"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4 }"
              placeholder="请输入您的问题..."
              resize="none"
              @keydown.enter.exact.prevent="handleSend"
              @keydown.enter.shift.exact.stop
            />
            <div class="va-input-actions">
              <el-button type="primary" :disabled="!canSend || sending" :loading="sending" @click="handleSend">
                发送
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, nextTick, reactive, ref } from 'vue'
import { ChatDotRound, Cpu, Delete, Plus, User } from '@element-plus/icons-vue'
import { getToken } from '@/utils/auth'

const genId = () => `${Date.now()}_${Math.random().toString(16).slice(2)}`

const sessions = ref([
  {
    id: 'default',
    title: '自我介绍',
    messages: [
      {
        id: genId(),
        role: 'assistant',
        content:
          '您好，我是智慧养老院的问答助手，主要职责是通过知识库解答与养老院服务相关的问题。请问有什么可以帮到您？'
      }
    ]
  }
])

const activeSessionId = ref(sessions.value[0].id)
const draft = ref('')
const chatScrollRef = ref()
const sending = ref(false)

const activeSession = computed(() => sessions.value.find(s => s.id === activeSessionId.value))
const activeMessages = computed(() => activeSession.value?.messages ?? [])
const canSend = computed(() => draft.value.trim().length > 0 && !!activeSession.value)

async function scrollToBottom() {
  await nextTick()
  const wrap = chatScrollRef.value?.wrapRef
  if (wrap) wrap.scrollTop = wrap.scrollHeight
}

function handleNewSession() {
  const id = genId()
  sessions.value.unshift({
    id,
    title: '新对话',
    messages: [
      {
        id: genId(),
        role: 'assistant',
        content: '您好，请描述您的问题。'
      }
    ]
  })
  activeSessionId.value = id
  scrollToBottom()
}

function handleDeleteSession(id) {
  const idx = sessions.value.findIndex(s => s.id === id)
  if (idx < 0) return

  sessions.value.splice(idx, 1)
  if (activeSessionId.value === id) {
    activeSessionId.value = sessions.value[0]?.id ?? ''
  }
  scrollToBottom()
}

async function handleSend() {
  if (!canSend.value || sending.value) return

  const session = activeSession.value
  if (!session) return

  const text = draft.value.trim()
  draft.value = ''

  const userMsgId = genId()
  session.messages.push({
    id: userMsgId,
    role: 'user',
    content: text
  })

  const assistantMsg = reactive({
    id: genId(),
    role: 'assistant',
    content: ''
  })
  session.messages.push(assistantMsg)

  sending.value = true
  scrollToBottom()

  try {
    const baseApi = import.meta.env.VITE_APP_BASE_API || ''
    const url = `${baseApi}/aiconsult/virtualagent/chat?prompt=${encodeURIComponent(text)}`
    const token = getToken()

    const resp = await fetch(url, {
      method: 'GET',
      headers: {
        ...(token ? { Authorization: `Bearer ${token}` } : {})
      }
    })

    if (!resp.ok) {
      assistantMsg.content = `请求失败：HTTP ${resp.status}`
      return
    }

    const contentType = resp.headers.get('content-type') || ''
    const reader = resp.body?.getReader()
    if (!reader) {
      assistantMsg.content = await resp.text()
      return
    }

    const decoder = new TextDecoder('utf-8')
    let buffer = ''
    let receivedAny = false
    const isSse = contentType.includes('text/event-stream')

    while (true) {
      const { value, done } = await reader.read()
      if (done) break

      const chunk = decoder.decode(value, { stream: true })
      if (!chunk) continue

      if (!isSse) {
        assistantMsg.content += chunk
        scrollToBottom()
        continue
      }

      buffer += chunk
      buffer = buffer.replace(/\r\n/g, '\n')

      while (buffer.includes('\n')) {
        const idx = buffer.indexOf('\n')
        const line = buffer.slice(0, idx)
        buffer = buffer.slice(idx + 1)

        if (!line.startsWith('data:')) continue
        const data = line.slice(5).trimStart()
        if (!data || data === '[DONE]') continue

        try {
          const json = JSON.parse(data)
          if (json.event === 'message') {
            const answer = json.answer || ''
            if (answer) {
              assistantMsg.content += answer
              receivedAny = true
              scrollToBottom()
            }
          } else if (json.event === 'message_end') {
            receivedAny = true
          }
        } catch (e) {
          assistantMsg.content += data
          receivedAny = true
          scrollToBottom()
        }
      }
    }

    if (isSse && buffer.startsWith('data:')) {
      const data = buffer.slice(5).trimStart()
      if (data && data !== '[DONE]') {
        try {
          const json = JSON.parse(data)
          if (json.event === 'message') {
            const answer = json.answer || ''
            if (answer) {
              assistantMsg.content += answer
              receivedAny = true
            }
          }
        } catch (e) {
          assistantMsg.content += data
          receivedAny = true
        }
      }
    }

    if (!assistantMsg.content && !receivedAny) {
      assistantMsg.content = '服务暂时不可用，请稍后重试。'
    }
  } catch (e) {
    assistantMsg.content = '接口调用失败，请稍后重试。'
  } finally {
    sending.value = false
    scrollToBottom()
  }
}

scrollToBottom()
</script>

<style lang="scss" scoped>
.virtual-agent {
  padding: 0;
}

.va-layout {
  display: flex;
  height: calc(100vh - 110px);
  background: #f5f7fa;
}

.va-left {
  width: 260px;
  background: #fff;
  border-right: 1px solid #ebeef5;
  display: flex;
  flex-direction: column;
}

.va-left-header {
  padding: 12px;
}

.va-new {
  width: 100%;
}

.va-left-section-title {
  padding: 8px 12px;
  font-size: 13px;
  color: #606266;
  font-weight: 600;
}

.va-session-list {
  flex: 1;
  padding: 0 8px 12px;
}

.va-session-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  padding: 10px 10px;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.15s ease;
}

.va-session-item:hover {
  background: #f5f7fa;
}

.va-session-item.active {
  background: rgba(64, 158, 255, 0.12);
}

.va-session-item-main {
  display: flex;
  align-items: center;
  gap: 8px;
  min-width: 0;
  flex: 1;
}

.va-session-icon {
  color: #409eff;
}

.va-session-title {
  font-size: 14px;
  color: #303133;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.va-session-del {
  flex: none;
}

.va-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 12px;
}

.va-chat {
  flex: 1;
  background: #fff;
  border-radius: 10px;
  border: 1px solid #ebeef5;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.va-chat-scroll {
  flex: 1;
  padding: 10px 14px;
}

.va-messages {
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding-bottom: 10px;
}

.va-msg {
  display: flex;
  gap: 10px;
  align-items: flex-start;
}

.va-msg.user {
  flex-direction: row-reverse;
}

.va-msg-avatar {
  width: 34px;
  height: 34px;
  border-radius: 50%;
  background: #f2f6fc;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #409eff;
  flex: none;
}

.va-msg.user .va-msg-avatar {
  background: #ecf5ff;
}

.va-msg-bubble {
  max-width: 74%;
  border-radius: 12px;
  padding: 10px 12px;
  line-height: 1.6;
  font-size: 14px;
  color: #303133;
  background: #f5f7fa;
  white-space: pre-wrap;
  word-break: break-word;
}

.va-msg.user .va-msg-bubble {
  background: #ecf5ff;
}

.va-msg-loading {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 2px 0;

  .dot {
    width: 8px;
    height: 8px;
    background: #909399;
    border-radius: 50%;
    animation: bounce 1.4s infinite ease-in-out both;

    &:nth-child(1) {
      animation-delay: -0.32s;
    }
    &:nth-child(2) {
      animation-delay: -0.16s;
    }
    &:nth-child(3) {
      animation-delay: 0;
    }
  }
}

@keyframes bounce {
  0%,
  80%,
  100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

.va-input {
  border-top: 1px solid #ebeef5;
  padding: 10px 12px;
}

.va-input-actions {
  display: flex;
  justify-content: flex-end;
  padding-top: 10px;
}
</style>