# JVM Day – Retries ⚡

<img src="https://img.shields.io/badge/Java-21-blue" alt="Java"/>
<img src="https://img.shields.io/badge/Spring-Boot-green" alt="Spring Boot"/>
<img src="https://img.shields.io/badge/Resilience4j-💪-orange" alt="Resilience4j"/>
<img src="https://img.shields.io/badge/Testing-WireMock-lightgrey" alt="WireMock"/>

📌 Repository with demo code for my talk at **JVM Day**:  
**“Retries: Love at the Third Attempt”**.  
It contains examples of retry strategies, patterns, and tests.  
And of course — the most important part of the talk: the applause code. 👏

---

## 📖 Table of Contents

- [Philosophy of Retries](#-philosophy-of-retries)
- [Strategies and Patterns](#-strategies-and-patterns)
- [Testing](#-testing)
- [How to Run](#-how-to-run)
- [Contacts](#-contacts)

---

## 🌱 Philosophy of Retries

> _"Fall down seven times, stand up eight."_ — Japanese proverb

Retries are not just algorithms.  
They are about **resilience**: in life, in engineering, in systems.

---

## 🔄 Strategies and Patterns

This repository contains implementations and tests for different retry strategies and patterns:

- **Fixed Delay Retry**
- **Incremental Backoff**
- **Exponential Backoff**
- **Immediate Retry**
- **Retry with Jitter**
- **Selective Retry (Conditional)**
- **Timeout-based Retry**
- **Max Budget Retry**

The code is presented in two ways:
- ✅ Manual implementation in Java
- ✅ Ready-to-use solutions with **Spring Retry** and **Resilience4j**

---

## 🧪 Testing

Testing is based on:

- **JUnit 5 + AssertJ** — unit tests
- **WireMock** — simulating external API errors
- **Spring Boot Test** — integration scenarios

Example cases:
- ✅ success after several failures
- ✅ no retries on `400 Bad Request`
- ✅ fallback after exhausting all attempts

---

## ▶ How to Run

There isn’t much to run here besides the tests — just execute them and explore the retry logic in action.

1. Clone the repository:
    ```bash
    git clone https://github.com/nutrolshok/retries.git
    cd retries

2. Run tests with Gradle:
    ```bash
   ./gradlew test
   
---

## 📬 Contacts

- 👨‍💻 Author: **Dmitriy Frolov**
- 📧 Email: [nutrolshok@mail.ru](mailto:nutrolshok@mail.ru)
- 💬 Telegram: [@nutrolshok](https://t.me/nutrolshok)
- 🖥️ GitHub: [nutrolshok](https://github.com/nutrolshok)

---

If you found this repository useful — don’t forget to give it a ⭐ on GitHub!