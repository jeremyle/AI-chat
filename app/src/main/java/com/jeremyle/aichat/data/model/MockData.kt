package com.jeremyle.aichat.data.model

val mockMessages = listOf(
    Message(
        content = "Hi! What can you help me with?",
        role = MessageRole.USER
    ),
    Message(
        content = """
            I can help you with a wide range of tasks:
            
            - **Coding** — write, debug, and explain code
            - **Writing** — drafts, emails, essays, summaries
            - **Research** — answer questions, explain concepts
            - **Math** — solve problems step by step
            - **Creative** — stories, brainstorming, ideas
            
            What would you like to work on?
        """.trimIndent(),
        role = MessageRole.ASSISTANT
    ),
    Message(
        content = "Can you explain what a ViewModel is in Android?",
        role = MessageRole.USER
    ),
    Message(
        content = """
            ## ViewModel
            
            A `ViewModel` is a class that holds and manages UI-related data. It survives configuration changes like screen rotation.
            
            ### Why use it?
            Without a ViewModel, rotating your phone destroys and recreates the Activity — losing all your data. The ViewModel lives longer than the UI.
            
            ### Example
```kotlin
            class ChatViewModel : ViewModel() {
                var messages by mutableStateOf(listOf<Message>())
                    private set
                
                fun sendMessage(content: String) {
                    messages = messages + Message(content, MessageRole.USER)
                }
            }
```
            
            ### In Compose
```kotlin
            @Composable
            fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
                val messages = viewModel.messages
            }
```
            
            > The ViewModel is the recommended way to manage state in Android apps.
        """.trimIndent(),
        role = MessageRole.ASSISTANT
    ),
    Message(
        content = "What's the difference between remember and mutableStateOf?",
        role = MessageRole.USER
    ),
    Message(
        content = """
            They do different things and are usually used together:
            
            ## mutableStateOf
            Creates an **observable state holder** — when the value changes, Compose recomposes any composable that reads it.
            
```kotlin
            val count = mutableStateOf(0)  // without remember
```
            
            ## remember
            **Survives recomposition** — without it, the value is recreated every time the composable redraws.
            
```kotlin
            val count = remember { mutableStateOf(0) }  // correct
```
            
            ## Together
            | | Survives recomposition | Triggers recomposition |
            |---|---|---|
            | `mutableStateOf` alone | ❌ | ✅ |
            | `remember` alone | ✅ | ❌ |
            | Both together | ✅ | ✅ |
            
            This is why you almost always see them combined.
        """.trimIndent(),
        role = MessageRole.ASSISTANT
    ),
    Message(
        content = "How do I make a network request in Android?",
        role = MessageRole.USER
    ),
    Message(
        content = """
            The most common approach is **Retrofit** with **coroutines**:
            
            ### 1. Add dependencies
```groovy
            implementation "com.squareup.retrofit2:retrofit:2.9.0"
            implementation "com.squareup.retrofit2:converter-gson:2.9.0"
```
            
            ### 2. Define the API interface
```kotlin
            interface ApiService {
                @POST("messages")
                suspend fun sendMessage(@Body request: MessageRequest): MessageResponse
            }
```
            
            ### 3. Create the Retrofit instance
```kotlin
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.example.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
```
            
            ### 4. Call it from a ViewModel
```kotlin
            viewModelScope.launch {
                val response = apiService.sendMessage(request)
            }
```
            
            > Always make network calls from a ViewModel using `viewModelScope`, never directly from a Composable.
        """.trimIndent(),
        role = MessageRole.ASSISTANT
    ),
    Message(
        content = "What is LazyColumn?",
        role = MessageRole.USER
    ),
    Message(
        content = """
            `LazyColumn` is Compose's equivalent of `RecyclerView` — it only renders items that are **currently visible** on screen.
            
            ### Basic usage
```kotlin
            LazyColumn {
                items(messages) { message ->
                    MessageBubble(message)
                }
            }
```
            
            ### Why not just Column?
            A regular `Column` renders **all items at once**, even if there are thousands. `LazyColumn` renders only what's visible, making it much more performant for long lists.
            
            ### Useful options
            - `reverseLayout = true` — start from bottom (great for chat)
            - `contentPadding` — padding around the whole list
            - `verticalArrangement` — spacing between items
        """.trimIndent(),
        role = MessageRole.ASSISTANT
    ),
    Message(
        content = "Thanks, this is really helpful!",
        role = MessageRole.USER
    ),
    Message(
        content = "You're welcome! Feel free to ask anything else.",
        role = MessageRole.ASSISTANT
    )
)