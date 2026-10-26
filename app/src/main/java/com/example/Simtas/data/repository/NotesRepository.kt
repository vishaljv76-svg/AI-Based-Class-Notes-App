package com.example.Simtas.data.repository

import com.example.Simtas.data.model.NoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Repository interface for managing notes data.
 * Adheres to MVVM architecture for clean data access.
 */
interface NotesRepository {
    fun getNotes(): Flow<List<NoteModel>>
}

/**
 * Default implementation of [NotesRepository].
 */
class NotesRepositoryImpl : NotesRepository {
    override fun getNotes(): Flow<List<NoteModel>> {
        return flowOf(emptyList())
    }
}
