package bridge.mock

import bridge.BridgeNumberGenerator


class TestNumberGenerator(numbers: List<Int>) : BridgeNumberGenerator {
    private val numbers: MutableList<Int> = numbers.toMutableList()

    override fun generate(): Int {
        return numbers.removeAt(0)
    }
}
