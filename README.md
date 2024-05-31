# Asclepius

Asclepius is an Android app that uses machine learning to identify skin cancer. Users can upload photos of skin lesions or moles, and the app analyzes them to assess the risk of cancer.

## Features

- **Machine Learning for Skin Cancer Detection:** Utilizes TensorFlow Lite for on-device image classification, ensuring quick and secure image analysis without relying on cloud services.
- **Scan Analysis History:** Saves scan analysis history using Room DB, allowing users to track their skin health over time.
- **Health News Updates:** Uses Retrofit to fetch the latest health news from News API, keeping users informed about the latest developments in health and wellness.

## Technical Details

### TensorFlow Lite for On-Device Image Classification

Asclepius uses TensorFlow Lite to perform image classification directly on the device. This ensures that image analysis is fast and secure, providing users with immediate results without the need for an internet connection.

### Scan Analysis History

The app employs Room DB to save and manage the history of scan analyses. This feature allows users to monitor changes in their skin health over time and ensures that all data is stored locally on the device for quick access and privacy.

### Health News Updates

Asclepius integrates Retrofit to fetch and display the latest health news from News API. This ensures that users stay informed about current health trends and information.

## Screenshots

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/dzikryaji/asclepius/blob/master/splash.png?raw=true" width="20%">
  <img src="https://github.com/dzikryaji/asclepius/blob/master/main.png?raw=true" width="20%">
  <img src="https://github.com/dzikryaji/asclepius/blob/master/main-gallery.png?raw=true" width="20%">
  <img src="https://github.com/dzikryaji/asclepius/blob/master/main-image.png?raw=true" width="20%">
</div>
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/dzikryaji/asclepius/blob/master/history.png?raw=true" width="20%">
  <img src="https://github.com/dzikryaji/asclepius/blob/master/result-cancer.png?raw=true" width="20%">
  <img src="https://github.com/dzikryaji/asclepius/blob/master/result-noncancer.png?raw=true" width="20%">
  <img src="https://github.com/dzikryaji/asclepius/blob/master/news.png?raw=true" width="20%">
</div>
