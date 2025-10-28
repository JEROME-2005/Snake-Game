Snake Game (Linked List Data Structure Implementation)

This project is a simple **Snake Game** implemented in Java.  
The main objective of this project is to demonstrate how a **Linked List** data structure can be used to represent and manage the snake's body while the game is running.

---
🧠 Data Structure Used

### **Linked List (via Deque / LinkedList Class)**

The snake's body is stored using:
```java
Deque<Point> snake = new LinkedList<>();

| Feature              | Implementation                       |
| -------------------- | ------------------------------------ |
| Programming Language | Java                                 |
| Data Structure       | LinkedList (through Deque interface) |
| Graphics / UI        | Java Swing (`JFrame` & `JPanel`)     |
| Game Loop            | `javax.swing.Timer`                  |
| Key   | Action     |
| ----- | ---------- |
| W / ↑ | Move Up    |
| S / ↓ | Move Down  |
| A / ← | Move Left  |
| D / → | Move Right |
