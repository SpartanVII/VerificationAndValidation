import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.stream.Stream;


class NerdleTest {
    static Nerdle nerdle;
    static String error;

    @BeforeAll
    static void setUp(){
        nerdle = new Nerdle();
        error = "";
    }

    static Stream<Arguments> dataForValidClass1() {
        Nerdle.SymbolHint[] res0 = new Nerdle.SymbolHint[8];
        Arrays.fill(res0, Nerdle.SymbolHint.CORRECT);

        Nerdle.SymbolHint[] res1 = res0.clone();
        res1[5]= Nerdle.SymbolHint.USELESS;
        res1[7]= Nerdle.SymbolHint.USELESS;

        Nerdle.SymbolHint[] res2 = new Nerdle.SymbolHint[8];
        Arrays.fill(res2, Nerdle.SymbolHint.USELESS);
        res2[0]= Nerdle.SymbolHint.CORRECT;
        res2[1]= Nerdle.SymbolHint.CORRECT;
        res2[2]= Nerdle.SymbolHint.CORRECT;
        res2[6]= Nerdle.SymbolHint.CORRECT;

        return Stream.of(
                Arguments.of("0005+2=7", "0005+2=7", false, res0),
                Arguments.of("0005+4=9", "0005+2=7", false, res1),
                Arguments.of("0005+4=9", "0003*2=6", false, res2)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForValidClass1")
    public void validClass1(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }

    static Stream<Arguments> dataForValidClass2() {
        Nerdle.SymbolHint[] res0 = new Nerdle.SymbolHint[6];
        Arrays.fill(res0, Nerdle.SymbolHint.CORRECT);

        Nerdle.SymbolHint[] res1 = res0.clone();
        res1[3]= Nerdle.SymbolHint.USELESS;
        res1[5]= Nerdle.SymbolHint.USELESS;

        Nerdle.SymbolHint[] res2 = new Nerdle.SymbolHint[6];
        Arrays.fill(res2, Nerdle.SymbolHint.USELESS);
        res2[0]= Nerdle.SymbolHint.CORRECT;
        res2[4]= Nerdle.SymbolHint.CORRECT;
        return Stream.of(
                Arguments.of("05+2=7", "05+2=7", true, res0),
                Arguments.of("05+4=9", "05+2=7", true, res1),
                Arguments.of("05+4=9", "03*2=6", true, res2)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForValidClass2")
    public void validClass2(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
            assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }

    static Stream<Arguments> dataForInvalidClass3() {
        return Stream.of(
                Arguments.of("455-5=450", "05+2=7", true, null),
                Arguments.of("5+4=9", "05+2=7", true, null),
                Arguments.of(" ", "05+2=7", true, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForInvalidClass3")
    public void invalidClass3(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }



    static Stream<Arguments> dataForInvalidClass4() {
        return Stream.of(
                Arguments.of("05+4=9", "455-5=450", true, null),
                Arguments.of("05+4=9", "5+2=7", true, null),
                Arguments.of("05+4=9", " ", true, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForInvalidClass4")
    public void invalidClass4(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }



    static Stream<Arguments> dataForInvalidClass5() {
        return Stream.of(
                Arguments.of("455-5=0450", "05+2=7", false, null),
                Arguments.of("5+4=9", "05+2=7", false, null),
                Arguments.of(" ", "05+2=7", false, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForInvalidClass5")
    public void invalidClass5(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }



    static Stream<Arguments> dataForInvalidClass6() {
        return Stream.of(
                Arguments.of("05+4=9", "455-5=0450", false, null),
                Arguments.of("05+4=9", "5+2=7", false, null),
                Arguments.of("05+4=9", " ", false, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForInvalidClass6")
    public void invalidClass6(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }

    static Stream<Arguments> dataForInvalidClass7() {
        return Stream.of(
                Arguments.of("05*4=9", "05+4=9", true, null),
                Arguments.of("/5*=8+", "05+2=7", true, null),
                Arguments.of("patata", "05+2=7", true, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForInvalidClass7")
    public void invalidClass7(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }

    static Stream<Arguments> dataForInvalidClass8() {
        return Stream.of(
                Arguments.of("05+2=7", "05*4=9", true, null),
                Arguments.of("05+2=7", "/5*=8+", true, null),
                Arguments.of("05+2=7", "patata", true, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForInvalidClass8")
    public void invalidClass8(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }


    static Stream<Arguments> dataForInvalidClass9() {
        return Stream.of(
                Arguments.of(null, "05+2=7", true, null),
                Arguments.of(null, "0005+4=9", false, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForInvalidClass9")
    public void invalidClass9(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }

    static Stream<Arguments> dataForInvalidClass10() {
        return Stream.of(
                Arguments.of("05+2=7", null, true, null),
                Arguments.of("0005+2=7", null, false, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForInvalidClass10")
    public void invalidClass10(String guess, String solution, boolean isMini, Nerdle.SymbolHint[] res){
        assertThat(nerdle.getHints(guess,solution,isMini), is(res));
    }



}