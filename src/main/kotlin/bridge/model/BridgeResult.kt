package bridge.model

data class BridgeResult(
    private val bridgeMap: BridgeMap = BridgeMap(),
    private var totalCount: Int = GAME_START_NUMBER
) {
    fun restart() {
        bridgeMap.clear()
        totalCount++
    }

    fun getTotalCount(): Int = totalCount

    fun update(result: BridgeStatus) {
        bridgeMap.update(result)
    }

    companion object {
        const val GAME_START_NUMBER = 1
    }

}