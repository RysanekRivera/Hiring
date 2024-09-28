import app.cash.turbine.test
import io.github.rysanekrivera.common.state.RemoteRequestState
import io.github.rysanekrivera.common.uiEvents.UiEvent
import io.github.rysanekrivera.common.uistate.UiState
import io.github.rysanekrivera.data.models.EmployeeDto
import io.github.rysanekrivera.domain.repositories.HiringRepository
import io.github.rysanekrivera.ui.viewmodels.HiringViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HiringViewModelTest {

    private lateinit var viewModel: HiringViewModel
    private val repository: HiringRepository = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HiringViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllEmployees should update state to success when repository call is successful`() = runTest {

        val rawList = listOf(EmployeeDto(1, 1, "Employee1"), EmployeeDto(2, 2, "Employee2"))
        val response = retrofit2.Response.success(rawList)
        whenever(repository.getAllEmployees()).thenReturn(response)

        viewModel.getAllEmployees()

        viewModel.state.test {
            assertEquals(UiState(allEmployeesRequestState = RemoteRequestState.Idle, isLoading = false), awaitItem())
            assertEquals(UiState(allEmployeesRequestState = RemoteRequestState.success(), allEmployeesList = response.body()?.map { it.toEmployee() } ?: emptyList()), awaitItem())
        }
    }

    @Test
    fun `getAllEmployees should update state to error when repository call fails`() = runTest {
        val expectedException = RuntimeException("Error")
        whenever(repository.getAllEmployees()).thenThrow(expectedException)

        viewModel.getAllEmployees()

        viewModel.state.test {
            assertEquals(UiState(isLoading = false), awaitItem())
            assertEquals(UiState(allEmployeesRequestState = RemoteRequestState.error(), error = expectedException), awaitItem())
        }
    }

    @Test
    fun `navigateToEmployeeDetails should emit NavigateToEmployeeDetails event`() = runTest {
        val employeeId = 1

        viewModel.navigateToEmployeeDetails(employeeId)

        viewModel.uiEvents.test {
            assertEquals(UiEvent.NavigateToEmployeeDetails(employeeId), awaitItem())
        }
    }

    @Test
    fun `onShowEmployeeSnackBar should emit ShowSnackBar event`() = runTest {
        val message = "Test message"

        viewModel.onShowEmployeeSnackBar(message)

        viewModel.uiEvents.test {
            assertEquals(UiEvent.ShowSnackBar(message), awaitItem())
        }
    }

    @Test
    fun `onShowAlertDialog should emit ShowAlertDialog event`() = runTest {

        val message = "Test Alert"
        val positiveText = "Yes"
        val neutralText = "Maybe"
        val negativeText = "No"
        val positiveAction = mock<() -> Unit>()
        val neutralAction = mock<() -> Unit>()
        val negativeAction = mock<() -> Unit>()

        viewModel.onShowAlertDialog(
            message,
            positiveText,
            negativeText,
            neutralText,
            positiveAction,
            negativeAction,
            neutralAction
        )

        viewModel.uiEvents.test {
            val event = awaitItem()
            if (event is UiEvent.ShowAlertDialog) {
                assertEquals(message, event.message)
                assertEquals(positiveText, event.positiveText)
                assertEquals(negativeText, event.negativeText)
                assertEquals(positiveAction, event.positiveAction)
            }
        }
    }
}