# 💬 WhatsApp Chatbot - Java + Firebase Integration

## 📌 About the Project
**WhatsApp Chatbot** is an intelligent Java-based chatbot system integrated with the **WhatsApp Business Cloud API**. It is capable of:
- Receiving and responding to messages in real-time
- Logging all interactions to **Firebase Firestore**
- Deployed as a **live backend server** using Railway

This project was developed as part of a Java Developer assignment to showcase backend integration skills with external APIs and real-time messaging platforms.

---

## 🚀 Live Demo

🟢 **Webhook/Backend URL**  
[`https://whatsapp-chat-bot-production-071a.up.railway.app/webhook`](https://whatsapp-chat-bot-production-071a.up.railway.app/webhook)

🟢 **GitHub Repo**  
[`github.com/NishantK04/whatsapp-chat-bot`](https://github.com/NishantK04/whatsapp-chat-bot)

---

## 🔥 Key Features

### ✅ 1. WhatsApp Integration (Meta API)
- Connects directly to the **WhatsApp Business Cloud API**
- Handles webhook verification and message reception

### ✅ 2. Real-time Reply System
- Auto-generates replies based on user input
- Replies sent back using WhatsApp's `messages` endpoint

### ✅ 3. Firebase Firestore Logging
- All incoming messages and replies are stored in a Firestore collection
- Format: `{ from, message, reply, timestamp }`

### ✅ 4. Deployed on Railway
- Free cloud deployment with auto-restart and logging
- Docker-based deployment using Java 17+ Maven

---

## 🛠️ Tech Stack

| Layer        | Tech                          |
|--------------|-------------------------------|
| Backend      | Java 17, Spring Boot          |
| Messaging    | WhatsApp Business Cloud API   |
| Database     | Firebase Firestore            |
| Hosting      | Railway                       |
| Build Tool   | Maven                         |

---

## 🧠 Sample Replies (Chatbot Logic)
```java
switch (message) {
  case "hi": return "Hello! 👋 How can I assist you today?";
  case "who made this bot": return "🤖 I was created by Nishant Kumar.";
  case "help": return "You can ask me about our services, timings, or anything else.";
  default: return "I'm not sure I understand. Try saying 'help'.";
}
