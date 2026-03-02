package com.supunsulakshana.kmpapp1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supunsulakshana.kmpapp1.api.HomeRepository
import com.supunsulakshana.kmpapp1.data.RandomUser
import com.supunsulakshana.kmpapp1.data.RandomUserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _users = MutableStateFlow<List<RandomUser>>(listOf())
    val users = _users.asStateFlow()

    private val homeRepository: HomeRepository = HomeRepository()

    init {
        viewModelScope.launch {
            homeRepository.getUsers().collect { users ->
                _users.update {
                    it + users
                }
            }
        }
    }
}