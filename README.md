# SimpleForm
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

- I am using view binding from here - https://medium.com/@Zhuinden/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c, cause as for me it's look more verbose then original implementation from google - https://developer.android.com/topic/libraries/view-binding
- Alse [`StateFlow`](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) could be used, but i preferred variant with `LiveData` in `ViewModels`
- `JUnit4` could be replaced by `JUnit5` but I don't think it makes a big difference
- For testing error use next credential - test@test.test, password = 12345678
