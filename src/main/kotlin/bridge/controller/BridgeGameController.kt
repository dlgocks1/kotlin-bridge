package bridge.controller

import bridge.BridgeGame
import bridge.BridgeMaker
import bridge.BridgeRandomNumberGenerator
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
            bridgeGame.updateMap(result)
            onFinish(result)
            onSuccess(result)
            onFail(result)
        } while (!gameStatus.onFinish())
    }

    private fun onFail(result: BridgeStatus) {
        with(bridgeGame) {
            result.isFail {
                outputView.printMap(getBridgeMap())
                if (retry(inputView.readGameCommand())) return@isFail
                setFinish()
            }
        }
    }

    private fun onSuccess(result: BridgeStatus) {
        result.isSuccess {
            outputView.printMap(bridgeGame.getBridgeMap())
        }
    }

    private fun onFinish(result: BridgeStatus) {
        with(bridgeGame) {
            result.isFinish {
                outputView.printEndMessage()
                outputView.printMap(getBridgeMap())
                outputView.printResult(result, getTotalCount())
                setFinish()
            }
        }
    }

    private fun setFinish() {
        gameStatus = GameStatus.FINISH
    }

}

