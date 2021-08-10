package fsm.pattern.custom;

import fsm.pattern.IAction;
import fsm.pattern.IContext;

public class FirstActionListener implements fsm.pattern.IActionListener {

    @Override
    public void onEntry(@SuppressWarnings("rawtypes") IAction action, IContext context) {
        System.out.println("in onEntry of ActionListener");
    }

    @Override
    public void onExit(@SuppressWarnings("rawtypes") IAction action, IContext context) {
        System.out.println("in onExit of ActionListener");
    }
}
