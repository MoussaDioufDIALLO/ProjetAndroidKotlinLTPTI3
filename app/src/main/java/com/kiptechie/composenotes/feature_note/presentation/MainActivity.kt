package com.kiptechie.composenotes.ui.theme.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kiptechie.composenotes.feature_note.data.domain.model.Note
import com.kiptechie.composenotes.ui.theme.presentation.add_edit_note.AddEditNoteScreen
import com.kiptechie.composenotes.ui.theme.presentation.notes.NotesScreen
import com.kiptechie.composenotes.ui.theme.presentation.util.Screen
import com.kiptechie.composenotes.ui.theme.ComposeNotesTheme
import dagger.hilt.android.AndroidEntryPoint

