import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

//class UserDetailsViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val repository: UserRepository
//    val allUserDetails: LiveData<List<UserDetails>>
//
//    init {
//        val userDetailsDao = UserDatabase.getDatabase(application).userDetailsDao()
//        repository = UserRepository(userDetailsDao)
//        allUserDetails = repository.allUserDetails
//    }
//
//    fun insert(userDetails: UserDetails) = viewModelScope.launch {
//        repository.insert(userDetails)
//    }
//}
