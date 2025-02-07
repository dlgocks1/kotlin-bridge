package bridge

import bridge.mock.TestNumberGenerator
import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BridgeMakerTest {

    private lateinit var bridgeMaker: BridgeMaker

    @BeforeEach
    fun setUp() {
        bridgeMaker = BridgeMaker(TestNumberGenerator(listOf(1, 0, 0, 1, 1)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["2", "21"])
    @DisplayName("입력받은 사이즈가 3미만, 20초과이면 예외를 발생한다.")
    fun makeWrongLengthBridge(size: Int) {
        assertThrows<IllegalArgumentException> {
            bridgeMaker.makeBridge(size)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["10", "15"])
    @DisplayName("만들어지는 다리가 U 또는 D가 포함된 형태로 반환되는지 테스트한다.")
    fun makeRightBridge(size: Int) {
        assertSimpleTest {
            assertThat(bridgeMaker.makeBridge(size)).hasSize(size)
            assertThat(bridgeMaker.makeBridge(size)).containsAnyOf("U", "D")
        }
    }

    @Test
    @DisplayName("완성된 다리가 예상된 형태와 같은지 테스트한다.")
    fun makeBridgeTest() {
        assertSimpleTest {
            assertThat(bridgeMaker.makeBridge(5)).isEqualTo(listOf("U", "D", "D", "U", "U"))
        }
    }

}