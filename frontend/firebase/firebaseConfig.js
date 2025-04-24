// src/firebase/firebaseConfig.js
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

// 👉 substitua pelos dados do seu projeto no Firebase Console
const firebaseConfig = {
  apiKey: "AIzaSyC7ku0Tn2bUSmCI7QAZhjLdtGtHDpM7SKw",
  authDomain: "go-habit-627db.firebaseapp.com",
  projectId: "go-habit-627db",
  storageBucket: "go-habit-627db.firebasestorage.app",
  messagingSenderId: "843758695870",
  appId: "1:843758695870:web:cc6d17ac184a528e0935ef",
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

export { auth };
