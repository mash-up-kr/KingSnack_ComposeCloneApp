package com.example.kingsnack

import androidx.compose.runtime.Composable
import com.example.kingsnack.ui.components.KingsnackScaffold
import com.example.kingsnack.ui.theme.KingSnackTheme

/**
 * @author 김현국
 * @created 2022/06/08
 */

@Composable
fun KingsnackApp() {
    KingSnackTheme {
        val appState = rememberKingsnackAppState()
        KingsnackScaffold(
            bottomBar = {
                if(appState.shouldShowBottomBar){

                }
            }
        ) {

        }
    }
}
