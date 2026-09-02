# 🚗 Instant Mechanic – Roadside Assistance & Service Booking App

A modern, fast, and intuitive Android application built for **Instant Mechanic** to help vehicle owners browse nearby garages, check working hours/services, and instantly request emergency or regular vehicle service assistance.

Built with **Kotlin**, **Jetpack Compose**, and **MVVM Architecture** following modern Android development best practices.

---

## 📱 App Highlights & Key Features

### 1. 🏠 Home & Discovery Screen
* **Verified Garages List:** Displays mechanic name, customer rating, distance, location, available services, and live **Open/Closed** status.
* **Smart Search & Category Filter:** Real-time filtering by garage name, service keyword, or quick category chips (*Emergency, Oil Change, Towing, etc.*).
* **Skeleton Shimmer Loading:** Smooth shimmer animation while data loads instead of boring default spinners.
* **Direct Call Dial:** 1-tap quick phone dialer launcher right from the garage card.

### 2. 📋 Mechanic Details Screen
* **Detailed Business Profile:** Address, active working hours, contact info, and complete list of offered services formatted as Material chips.
* **One-Tap Share:** Android Native Share (`ACTION_SEND`) to easily share garage details via WhatsApp, SMS, or other apps.
* **Direct Booking CTA:** Prominent call-to-action button to open the service request form.

### 3. 📝 Service Request & Live Form Validation
* **Interactive Booking Form:** Input fields for Customer Name, Mobile Number, Vehicle Number, Service selection dropdown, and problem description.
* **Real-time Validation:** Live error states ensuring proper 10-digit Indian phone numbers and mandatory fields before submission.
* **Confirmation Feedback:** Material 3 alert dialog confirming the request dispatch.

### 4. ⚡ Active Bookings Dashboard
* **Dynamic State Management:** Active service requests instantly reflect at the top of the Home Screen.
* **Cancel Request Action:** Allows users to cancel their active service request anytime.

---

## 🛠️ Tech Stack & Architecture

* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI Toolkit:** [Jetpack Compose (Material 3)](https://developer.android.com/jetpack/compose)
* **Architecture:** MVVM (Model-View-ViewModel) + Unidirectional Data Flow (UDF)
* **Asynchronous & State:** Kotlin Coroutines & `StateFlow`
* **Navigation:** Jetpack Navigation Compose
* **Design & Icons:** Material Icons Extended & Custom Gradients

---

## 🏛️ Architecture Flow

```text
       ┌───────────────┐
       │   Compose UI  │  (Screens, Composables)
       └───────▲───────┘
               │ Observes State (StateFlow)
               │ Sends Events / User Actions
       ┌───────▼───────┐
       │   ViewModel   │  (MechanicViewModel - Manages UI States)
       └───────▲───────┘
               │ Requests Data / Sends Booking
       ┌───────▼───────┐
       │  Repository   │  (MechanicRepository - Mock API Layer / Local Cache)
       └───────▲───────┘
               │
       ┌───────▼───────┐
       │  Data Model   │  (Mechanic, ServiceRequest)
       └───────────────┘