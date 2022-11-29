package bridge.model

enum class GameStatus {
    FINISH,
    ONPLAY;

    fun onFinish(): Boolean {
        return this == FINISH
    }
}