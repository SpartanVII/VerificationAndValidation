import com.fathzer.soft.javaluator.DoubleEvaluator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.beans.Expression;
import java.util.Arrays;


public class Nerdle {
    public static final int NORMAL_LENGTH = 8;
    public static final int MINI_LENGTH = 6;
    public enum SymbolHint {
        USELESS,
        MISPLACED,
        CORRECT
    }
    public boolean validateExpression(String expression) {
        String[] comp = expression.split("=");
        if (comp.length!=2) return false;

        DoubleEvaluator evaluator = new DoubleEvaluator();
        try {
            if(evaluator.evaluate(comp[0])==Integer.parseInt(comp[1])){
                return true;
            }
        }catch (Exception e){
            return false;
        }


        return false;
    }
    public SymbolHint[] getHints(String guess, String solution, boolean isMini) {
        // Not yet implemented...
        if (guess == null || solution == null){
            return null;
        }

        if((isMini && (guess.length()!=6 || solution.length()!=6)) || (!isMini && (guess.length()!=8 || solution.length()!=8))){
            return null;
        }

        if(!validateExpression(guess) || !validateExpression(solution)){
            return null;
        }

        SymbolHint[] dev = new SymbolHint[isMini?6:8];
        Arrays.fill(dev, SymbolHint.USELESS);

        for (int i = 0 ; i<solution.length(); i++){
            assert guess != null;
            System.out.println(guess.subSequence(i,i+1));
            if (guess.charAt(i)==solution.charAt(i)) dev[i] = SymbolHint.CORRECT;
            else if(solution.contains(guess.subSequence(i,i+1))) dev[i] = SymbolHint.MISPLACED;
        }

        return dev;
    }
}
