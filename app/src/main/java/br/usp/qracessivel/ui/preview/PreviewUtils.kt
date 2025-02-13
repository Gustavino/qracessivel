package br.usp.qracessivel.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import br.usp.qracessivel.viewmodel.MainContract
import br.usp.qracessivel.viewmodel.QrCodeState

@Composable
fun previewViewModel(state: QrCodeState = QrCodeState.Scanning): MainContract {
    return remember { FakeMainViewModel(state) }
}