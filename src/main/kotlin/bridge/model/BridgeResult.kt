package bridge.model

data class BridgeResult(
    private val bridgeMap: BridgeMap = BridgeMap(),
) {
    var totalCount: Int = GAME_START_NUMBER
        private set

    fun restart() {
        bridgeMap.clear()
        totalCount++
    }

    fun update(result: BridgeStatus) {
        bridgeMap.update(result)
    }

    companion object {
        const val GAME_START_NUMBER = 1
    }

}