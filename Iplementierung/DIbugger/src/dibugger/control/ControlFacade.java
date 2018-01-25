package dibugger.control;

import java.io.File;
import java.util.List;
import java.util.Objects;

import dibugger.debuglogic.debugger.DebugLogicFacade;
import dibugger.debuglogic.debugger.ProgramInput;
import dibugger.debuglogic.exceptions.DIbuggerLogicException;
import dibugger.debuglogic.interpreter.ScopeTuple;
import dibugger.filehandler.exceptions.FileHandlerException;
import dibugger.filehandler.exceptions.LanguageNotFoundException;
import dibugger.userinterface.GUIFacade;

/**
 *  Provides an interface for this package's functionality.
 */
public class ControlFacade {
    private boolean isInDebugMode;

    private DebugLogicController debugLogicController;
    private ExceptionHandler exceptionHandler;
    private FileHandlerInteractor fileHandlerInteractor;

    /**
     * Creates a new ControlFacade object.
     * Change in presentation of DIbugger's model component will be triggered
     * by given GUIFacade.
     * 
     * @param guiFacade a UI-facade of DIbugger
     */
    public ControlFacade(GUIFacade guiFacade) {
        disableDebugMode();
        Objects.requireNonNull(guiFacade);
        debugLogicController = new DebugLogicController();
        debugLogicController.attachToModel(guiFacade);
        try {
            fileHandlerInteractor = new FileHandlerInteractor(debugLogicController, guiFacade);
        } catch (FileHandlerException exception) {
            System.exit(0);
        }
        exceptionHandler = new ExceptionHandler(fileHandlerInteractor, guiFacade);
    }

    boolean isInDebugMode() {
        return isInDebugMode;
    }

    private void enableDebugMode() {
        isInDebugMode = true;
    }

    private void disableDebugMode() {
        isInDebugMode = false;
    }

    private void ensureInDebugMode() {
        if (!isInDebugMode()) {
            throw new IllegalStateException();
        }
    }

    private void ensureNotInDebugMode() {
        if (isInDebugMode()) {
            throw new IllegalStateException();
        }
    }

    /**
     * Sets the stepsize of a program
     * 
     * @param numberOfProgram
     *            the number of program to change the stepsize of
     * @param size
     *            the new stepsize to use while debugging
     * @see DebugLogicController#setStepSize(int, int)
     */
    public void setStepSize(int numberOfProgram, int size) {
        debugLogicController.setStepSize(numberOfProgram, size);
    }

    /**
     * Executes a step defined by a given step type.
     * 
     * @param type
     *            the type of the step
     * @see DebugLogicController#step(int)
     */
    public void step(int type) {
        ensureInDebugMode();
        try {
            debugLogicController.step(type);
        } catch (DIbuggerLogicException exception) {
            exceptionHandler.handle(exception);
        }
    }

    /**
     * Continues Debugging / Stepping in all programs until a Breakpoint or
     * Conditional Breakpoint is reached.
     * 
     * @see DebugLogicController#continueDebug()
     */
    public void continueDebug() {
        ensureInDebugMode();
        try {
            debugLogicController.continueDebug();
        } catch (DIbuggerLogicException exception) {
            exceptionHandler.handle(exception);
        }
    }

    /**
     * Executes a normal step of size 1 in a given program
     * 
     * @param numberOfProgram
     *            the number of program to do a step in
     * @see DebugLogicController#singleStep(int) 
     */
    public void singleStep(int numberOfProgram) {
        ensureInDebugMode();
        debugLogicController.singleStep(numberOfProgram);
    }

    /**
     * Causes DIbugger to perform a step in reverse order of execution
     * of each program's instructions.
     * 
     * @see DebugLogicController#stepBack()
     */
    public void stepBack() {
        ensureInDebugMode();
        try {
            debugLogicController.stepBack();
        } catch (DIbuggerLogicException exception) {
            exceptionHandler.handle(exception);
        }
    }

    /**
     * Creates a new watch expression.
     * 
     * @param id
     *            the id of the watch expression
     * @param expr
     *            the expression of the watch expression
     * @see DebugLogicController#createWatchExpression(int, String)
     */
    public void createWatchExpression(int watchExpressionId, String expression) {
        debugLogicController.createWatchExpression(watchExpressionId, expression);
    }
    
    /**
     * Changes the specified watch expression.
     * 
     * @param id
     *            the id of the watch expression to change
     * @param expr
     *            the new expression
     * @param scopes
     *            a list of scopes for the new watch expression
     * @see DebugLogicController#changeWatchExpression(int, String, List)
     */
    public void changeWatchExpression(int watchExpressionId, String expression, List<ScopeTuple> scopes) {
        debugLogicController.changeWatchExpression(watchExpressionId, expression, scopes);
    }

    /**
     * Deletes the specified watch expression.
     * 
     * @param id
     *            the id of the watch expression
     * @see DebugLogicController#deleteWatchExpression(int)
     */
    public void deleteWatchExpression(int watchExpressionId) {
        debugLogicController.deleteWatchExpression(watchExpressionId);
    }

    /**
     * Creates a new conditional breakpoint.
     * 
     * @param id
     *            the id of the breakpoint
     * @param cond
     *            the condition of the breakpoint
     * @see DebugLogicController#createConditionalBreakpoint(int, String)            
     */
    public void createConditionalBreakpoint(int breakPointId, String condition) {
        debugLogicController.createConditionalBreakpoint(breakPointId, condition);
    }

    /**
     * Changes the specified conditional breakpoint.
     * 
     * @param id
     *            the id of the breakpoint to change
     * @param cond
     *            the condition of the breakpoint
     * @param scopes
     *            a list of all scopes
     * @see DebugLogicController#changeConditionalBreakpoint(int, String, List)
     */
    public void changeConditionalBreakpoint(int breakPointId, String condition, List<ScopeTuple> scopes) {
        debugLogicController.changeConditionalBreakpoint(breakPointId, condition, scopes);
    }

    /**
     * Deletes specified breakpoint.
     * 
     * @param id
     *            the id of the breakpoint
     * @see DebugLogicController#deleteConditionalBreakpoint(int)
     */
    public void deleteConditionalBreakpoint(int conditionalBreakPointId) {
        debugLogicController.deleteConditionalBreakpoint(conditionalBreakPointId);
    }

    /**
     * Creates a breakpoint at given line in all programs known to DIbugger.
     * 
     * @param line the line to create a breakpoint at
     * @see DebugLogicController#createSynchronousBreakpoint(int) 
     */
    public void createSynchronousBreakpoint(int line) {
        debugLogicController.createSynchronousBreakpoint(line);
    }

    /**
     * Creates a new breakpoint at given line of specified program.
     * 
     * @param numberOfProgram
     *            the program's number
     * @param line
     *            the line to create a breakpoint at
     * @see DebugLogicController#createBreakpoint(int, int)
     */
    public void createBreakpoint(int numberOfProgram, int line) {
        debugLogicController.createBreakpoint(numberOfProgram, line);
    }

    /**
     * Deletes a breakpoint at given line of specified program.
     * 
     * @param numberOfProgram
     *            the number of program to delete a breakpoint of
     * @param line
     *            the line number referring to the breakpoint
     * @see DebugLogicController#deleteBreakpoint(int, int)
     */
    public void deleteBreakpoint(int numberOfProgram, int line) {
        debugLogicController.deleteBreakpoint(numberOfProgram, line);
    }

    /**
     * Deletes all breakpoints in all programs.
     * 
     * @see DebugLogicController#deleteAllBreakpoints()
     */
    public void deleteAllBreakpoints() {
        debugLogicController.deleteAllBreakpoints();
    }

    public void saveText(List<String> inputVariables, List<String> programTexts, List<String> programIdentifiers) {
        debugLogicController.saveText(inputVariables, programTexts, programIdentifiers);
    }

    public void startDebug() throws DIbuggerLogicException {
        enableDebugMode();
        debugLogicController.startDebug();
    }

    public void stopDebug() {
        disableDebugMode();
        debugLogicController.stopDebug();
    }

    /**
     * Partially resets state of DIbugger's model component.
     * 
     * @see DebugLogicController#reset()
     */
    public void reset() {
        debugLogicController.reset();
    }

    public void loadConfiguration(File configurationFile) {
        ensureNotInDebugMode();

        try {
            fileHandlerInteractor.loadConfigurationFile(configurationFile);
        } catch (FileHandlerException exception) {
            exceptionHandler.handle(exception);
        }
    }

    public void saveConfiguration(File configurationFile) {
        fileHandlerInteractor.saveConfiguration(configurationFile);
    }

    /**
     * Prompts this' GUIFacade to display a specified program's text.
     * 
     * @param file the file containing the text
     * @return the text contained in file
     * @see FileHandlerInteractor#loadProgramText(File)
     */
    public String loadProgramText(File file) {
        ensureNotInDebugMode();
        return fileHandlerInteractor.loadProgramText(file);
    }

    /**
     * Returns a list containing all languages available for use by this'
     * GUIFacade
     * 
     * @return a list containing all languages available
     * @see FileHandlerInteractor#getAvailableLanuages()
     */
    public List<String> getAvailableLanuages() {
        return fileHandlerInteractor.getAvailableLanuages();
    }

    /**
     * Sets the maximum-iteration-count (example: while loop).
     * 
     * @param count
     *            the new maximum
     * @see DebugLogicController#setMaximumIterations(int)
     */
    public void setMaximumIterations(int maximum) {
        debugLogicController.setMaximumIterations(maximum);
    }

    /**
     * Sets the upper limit of function calls allowed when executing
     * a program.
     * 
     * @param count
     *            the new maximum
     * @see DebugLogicController#setMaximumFunctionCalls(int)
     */
    public void setMaximumFunctionCalls(int maximum) {
        debugLogicController.setMaximumFunctionCalls(maximum);
    }

    /**
     * Suggest a stepsize for all programs.
     * 
     * @param programText
     *            list containing all program texts
     * @see DebugLogicController#suggestStepSize()
     */
    public void suggestStepSize() {
        debugLogicController.suggestStepSize();
    }

    /**
     * Suggests a watch expression.
     * 
     * @return String representing the expression
     * @see DebugLogicController#suggestWatchExpression(List)
     */
    public String suggestWatchExpression(List<ProgramInput> programInput) {
        return debugLogicController.suggestWatchExpression(programInput);
    }

    /**
     * Suggests a conditional breakpoint.
     * 
     * @return String representing the condition
     * @see DebugLogicController#suggestConditionalBreakpoint(List)
     */
    public String suggestConditionalBreakpoint(List<ProgramInput> programInput) {
        return debugLogicController.suggestConditionalBreakpoint(programInput);
    }

    /**
     * Suggests an InputValue for a given variable in a given range.
     * 
     * @param identifier
     *            the variable's name
     * @param range
     *            the range containing the value to suggest
     * @param type
     *            the type of the variable
     * @return String representing the suggestion value
     * @see DebugLogicController#suggestInputValue(String, String, int)
     */
    public String suggestInputValue(String inputVariableId, String range, int type) {
        return debugLogicController.suggestInputValue(inputVariableId, range, type);
    }

    /**
     * Select a strategy to be used to suggest step sizes.
     * 
     * @param id
     *            the strategy id to select
     * @see DebugLogicController#selectStepSizeStrategy(int)
     */
    public void selectStepSizeStrategy(int stepSizeStrategyId) {
        debugLogicController.selectStepSizeStrategy(stepSizeStrategyId);
    }

    /**
     * Select a strategy to be used to suggest realtional expressions
     * 
     * @param id
     *            the strategy id to select
     * @see DebugLogicController#selectRelationalExpressionStrategy(int)
     */
    public void selectRelationalExpressionStrategy(int expressionStrategyId) {
        debugLogicController.selectRelationalExpressionStrategy(expressionStrategyId);
    }

    /**
     * Select a strategy to be used to suggest input values
     * 
     * @param id
     *            the strategy id to select
     * @see DebugLogicController#selectInputValueStrategy(int)
     */
    public void selectInputValueStrategy(int inputValueStrategyId) {
        debugLogicController.selectInputValueStrategy(inputValueStrategyId);
    }

    /**
     * Changes the language in which information is shown by
     * this' GUIFacade.
     * 
     * @param languageId the id specifieng the language
     * @see FileHandlerInteractor#changeLanguage(String)
     */
    public void changeLanguage(String languageId) {
        try {
            fileHandlerInteractor.changeLanguage(languageId);
        } catch (LanguageNotFoundException exception) {
            exceptionHandler.handle(exception);
        }
    }

    /**
     * Returns the DebugLogicFacade known to this ControlFacade.
     * 
     * @return the DebugLogicFacade known to this
     */
    public DebugLogicFacade getDebugLogicFacade() {
        return debugLogicController.getDebugLogicFacade();
    }
}
