package br.usp.qracessivel.service

import androidx.camera.core.Camera
import androidx.camera.core.TorchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TorchService {
    private var camera: Camera? = null

    private val _torchState = MutableStateFlow(false)
    val torchState: StateFlow<Boolean> = _torchState.asStateFlow()

    fun setCamera(camera: Camera) {
        this.camera = camera
        _torchState.value = camera.cameraInfo.torchState.value == TorchState.ON
    }

    fun toggleTorch() {
        camera?.let { cam ->
            if (cam.cameraInfo.hasFlashUnit()) {
                val newState = !_torchState.value
                cam.cameraControl.enableTorch(newState)
                _torchState.value = newState
            }
        }
    }
}