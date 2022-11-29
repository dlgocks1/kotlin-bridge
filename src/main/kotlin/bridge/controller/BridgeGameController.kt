package bridge.controller

import bridge.BridgeGame
import bridge.BridgeMaker
import bridge.BridgeRandomNumberGenerator
import bridge.model.BridgeResult
import bridge.model.BridgeStatus
import bridge.model.GameStatus
import bridge.view.InputView
import bridge.view.OutputView

class BridgeGameController(
    private val inputView: InputView,
    private val outputView: OutputView
) {

    private val bridgeGame: BridgeGame
    private var gameStatus: GameStatus = GameStatus.ONPLAY
    private val bridgeResult = BridgeResult()

    init {
        inputView.gameStart()
        bridgeGame = BridgeGame(
            size = inputView.readBridgeSize(),
            bridgeMaker = BridgeMaker(BridgeRandomNumberGenerator())
        )
    }

    fun play() {
        do {
            val result = bridgeGame.move(inputView.readMoving())
            bridgeResult.update(result)
            onFinish(result)
            onSuccess(result)
            onFail(result)
        } while (!gameStatus.onFinish())
    }

    private fun onFail(result: BridgeStatus) {
        result.isFail {
            outputView.printMap(bridgeResult)
            if (bridgeGame.retry(inputView.readGameCommand())) {
                bridgeResult.restart()
                return@isFail
            }
            setFinish()
        }
    }

    private fun onSuccess(result: BridgeStatus) {
        result.isSuccess {
            outputView.printMap(bridgeResult)
        }
    }

    private fun onFinish(result: BridgeStatus) {
        result.isFinish {
            outputView.printEndMessage()
            outputView.printMap(bridgeResult)
            outputView.printResult(result, bridgeResult.getTotalCount())
            setFinish()
        }
    }

    private fun setFinish() {
        gameStatus = GameStatus.FINISH
    }

}

