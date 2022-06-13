package com.example.kingsnack.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

/**
 * @author 김현국
 * @created 2022/06/13
 */
/*
현재 레이아웃 방향에 따라 올바른 아이콘을 반환
 */
@Composable
fun mirroringIcon(ltrIcon: ImageVector, rtlIcon: ImageVector): ImageVector =
    if (LocalLayoutDirection.current == LayoutDirection.Ltr) ltrIcon else rtlIcon /*
현재 레이아웃 방향에 따라 올바른 back 네비게이션 아이콘반환
 */
@Composable
fun mirroringBackIcon() = mirroringIcon(ltrIcon = Icons.Outlined.ArrowBack, rtlIcon = Icons.Outlined.ArrowForward)
