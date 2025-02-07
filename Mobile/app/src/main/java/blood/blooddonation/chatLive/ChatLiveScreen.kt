package blood.blooddonation.chatLive

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.R
import blood.blooddonation.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatLiveScreen( goBack: () -> Unit ) {
    val chatLiveViewModel = viewModel<ChatLiveViewModel>(factory = ChatLiveViewModel.Factory)
    val chatLiveUiState by chatLiveViewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = chatLiveUiState.questions.size, key2 = chatLiveUiState.answers.size) {
        if (chatLiveUiState.questions.isNotEmpty() && chatLiveUiState.answers.isNotEmpty()) {
            lazyListState.animateScrollToItem(index = chatLiveUiState.questions.size - 1)
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(AppColor.RedGrenaline, AppColor.WhiteLevenderBlush),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.chatLive),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp,
                        color = AppColor.BlackSoot
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        onClick = { goBack() }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AppColor.BlackSoot
                        )
                    }
                },
                actions = {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(gradient)
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = lazyListState
            ) {
                itemsIndexed(items = chatLiveUiState.questions.zip(chatLiveUiState.answers)) { index, pair ->
                    ChatMessage(question = pair.first, answer = pair.second)
                }
            }
            UserInputField(onMessageSent = { message ->
                chatLiveViewModel.getChatBotAnswer(message)
            })
        }
    }
}

@Composable
fun ChatMessage(question: String, answer: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Card(
            colors = CardDefaults.cardColors(AppColor.RedFirebrick),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .widthIn(max = 300.dp)  // maxLength
        ) {
            Text(
                text = question,
                color = AppColor.BlackSoot,
                modifier = Modifier.padding(8.dp),
                style = TextStyle(fontSize = 16.sp)
            )
        }

        Spacer(Modifier.height(4.dp))

        Card(
            colors = CardDefaults.cardColors(AppColor.RedLipstick),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .widthIn(max = 300.dp)
        ) {
            Text(
                text = answer,
                color = AppColor.WhiteLevenderBlush,
                modifier = Modifier.padding(8.dp),
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputField(onMessageSent: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(36.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = AppColor.BlackSoot,
                cursorColor = AppColor.BlackSoot,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = AppColor.WhiteLevenderBlush
            ),
            shape = RoundedCornerShape(36.dp),
            placeholder = {
                Text(
                    text = "Scrie o întrebare...",
                    color = Color.Gray
                )
                          },
            maxLines = 3,
            singleLine = false
        )
        Button(
            onClick = {
                onMessageSent(text)
                text = ""
            },
            modifier = Modifier.padding(start = 8.dp),
            enabled = text.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            Icon(
                Icons.Filled.Send,
                contentDescription = "Send",
                tint = AppColor.BlackSoot
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatLiveScreenPreview() {
    ChatLiveScreen(goBack = {})
}