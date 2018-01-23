package dibugger.userinterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;




public class ProgramPanel extends JPanel {

  private String id;

  private JLabel programName;
  private JLabel stepsize;
  private JTextField stepsizeInput;
  private JLabel inputvariablesLabel;
  private JTextField inputvariableTextField;

  private JPanel codePanel;
  private JScrollPane codeScrollPane;
  private JTextPane codeTextArea;
  private JTextPane lines;
  private List<JRadioButton> breakpointButtons;

  private JPanel variableInspector;
  DefaultListModel<String> listModel;
  private JScrollPane variableInspectorScrollPane;
  private JList<String> variableInspectorList;


  /**
   * Constructor for a nem ProgramPanel.
   *
   * @param identifier identifier of program panel
   */
  public ProgramPanel(String identifier) {
    id = identifier;
    initComponents();
  }

  /**
   * initializes main components of program panel.
   */
  private void initComponents() {
    programName = new JLabel();
    stepsize = new JLabel();
    stepsizeInput = new JTextField();
    inputvariablesLabel = new JLabel();
    inputvariableTextField = new JTextField();
    codeScrollPane = new JScrollPane();

    programName.setText("Programm: " + id);

    stepsize.setText("Stepsize: ");

    stepsizeInput.setText("jTextField2");
    stepsizeInput.setPreferredSize(new Dimension(40, 40));
    stepsizeInput.addActionListener(this::stepsizeInputActionPerformed);

    inputvariablesLabel.setText("Eingabevariablen: ");

    inputvariableTextField.setText("jTextField1");
    inputvariableTextField.addActionListener(this::variableInputActionPerformed);
    inputvariableTextField.setPreferredSize(new Dimension(288, 40));


    initCodeArea();

    initVariableInspector();

    GroupLayout firstTextPanelLayout = new GroupLayout(this);
    setLayout(firstTextPanelLayout);
    firstTextPanelLayout.setHorizontalGroup(
        firstTextPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(firstTextPanelLayout.createSequentialGroup()
                .addGroup(firstTextPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(firstTextPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING, false)
                        .addGroup(firstTextPanelLayout.createSequentialGroup()
                            .addComponent(stepsize)
                            .addComponent(stepsizeInput, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(programName)
                        .addGroup(firstTextPanelLayout.createSequentialGroup()
                            .addComponent(inputvariablesLabel)
                            .addComponent(inputvariableTextField)))
                    .addComponent(codePanel)
                    .addComponent(variableInspector))
            )
    );
    firstTextPanelLayout.setVerticalGroup(
        firstTextPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(firstTextPanelLayout.createSequentialGroup()
                .addComponent(programName)
                .addGap(15, 15, 15)
                .addGroup(firstTextPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(stepsize)
                    .addComponent(stepsizeInput, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(firstTextPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(inputvariablesLabel)
                    .addComponent(inputvariableTextField,
                        GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(codePanel, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(variableInspector)

            )
    );
  }

  private void stepsizeInputActionPerformed(ActionEvent evt) {
    // TODO add your handling code here:
  }

  private void variableInputActionPerformed(ActionEvent evt) {
    // TODO add your handling code here:
  }

  /**
   * initializes code area of program panel.
   */
  private void initCodeArea() {
    JPanel breakpointButtonPanel = new JPanel();

    SpringLayout breakpointPanelLayout = new SpringLayout();
    breakpointButtonPanel.setLayout(breakpointPanelLayout);


    codeScrollPane = new JScrollPane();
    lines = new JTextPane();
    lines.setText("1");
    codeTextArea = new JTextPane();
    lines.setBackground(Color.YELLOW);
    lines.setEditable(false);
    lines.setFont(lines.getFont().deriveFont(16.509f));
    codeTextArea.setFont(codeTextArea.getFont().deriveFont(16.509f));
    codeTextArea.getDocument().addDocumentListener(new DocumentListener() {
      String getText() {
        int caretPosition = codeTextArea.getDocument().getLength();
        Element root = codeTextArea.getDocument().getDefaultRootElement();
        String text = "1" + System.getProperty("line.separator");
        for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
          text += i + System.getProperty("line.separator");
        }
        return text;
      }

      @Override
      public void changedUpdate(DocumentEvent de) {

      }

      @Override
      public void insertUpdate(DocumentEvent de) {
        int lineCount = codeTextArea.getText().split(System.getProperty("line.separator")).length;
        if (lineCount != breakpointButtons.size()) {
          lines.setText(getText());
          breakpointButtons.add(new JRadioButton());
          breakpointButtons.get(breakpointButtons.size() - 1)
              .setPreferredSize(new Dimension(20, 20));
          breakpointPanelLayout.putConstraint(SpringLayout.WEST,
              breakpointButtons.get(breakpointButtons.size() - 1), 0,
              SpringLayout.WEST, breakpointButtons.get(breakpointButtons.size() - 2));
          breakpointPanelLayout.putConstraint(SpringLayout.NORTH,
              breakpointButtons.get(breakpointButtons.size() - 1), 20,
              SpringLayout.NORTH, breakpointButtons.get(breakpointButtons.size() - 2));
          breakpointButtonPanel.add(breakpointButtons.get(breakpointButtons.size() - 1));
          breakpointButtonPanel.updateUI();
        }
      }

      @Override
      public void removeUpdate(DocumentEvent de) {
        int lineCount = codeTextArea.getText().split(System.getProperty("line.separator")).length;
        if (lineCount != breakpointButtons.size()) {
          lines.setText(getText());
          breakpointButtonPanel.removeAll();
          breakpointPanelLayout.putConstraint(SpringLayout.WEST, breakpointButtons.get(0),
              0,
              SpringLayout.WEST, breakpointButtonPanel);
          breakpointPanelLayout.putConstraint(SpringLayout.NORTH, breakpointButtons.get(0),
              1,
              SpringLayout.NORTH, breakpointButtonPanel);
          breakpointButtonPanel.add(breakpointButtons.get(0));
          int caretPosition = codeTextArea.getDocument().getLength();
          Element root = codeTextArea.getDocument().getDefaultRootElement();
          for (int i = 1; i < root.getElementIndex(caretPosition) + 1; i++) {
            breakpointPanelLayout.putConstraint(SpringLayout.WEST, breakpointButtons.get(i),
                0,
                SpringLayout.WEST, breakpointButtons.get(i - 1));
            breakpointPanelLayout.putConstraint(SpringLayout.NORTH, breakpointButtons.get(i),
                20,
                SpringLayout.NORTH, breakpointButtons.get(i - 1));
            breakpointButtonPanel.add(breakpointButtons.get(i));
          }
          for (int i = root.getElementIndex(caretPosition);
               i < breakpointButtons.size(); i++) {
            breakpointButtons.remove(i);
          }
          breakpointButtonPanel.updateUI();
        }
      }

    });

    codeTextArea.setPreferredSize(new Dimension(400, 800));
    codeScrollPane.getViewport().add(codeTextArea);

    codeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    codeScrollPane.setPreferredSize(new Dimension(400, 800));

    codePanel = new JPanel();


    breakpointButtons = new ArrayList<>();
    breakpointButtons.add(new JRadioButton());
    breakpointButtons.get(0).setPreferredSize(new Dimension(20, 20));
    breakpointButtonPanel.setPreferredSize(new Dimension(20, 100000000));
    breakpointPanelLayout.putConstraint(SpringLayout.WEST, breakpointButtons.get(0),
        0,
        SpringLayout.WEST, breakpointButtonPanel);
    breakpointPanelLayout.putConstraint(SpringLayout.NORTH, breakpointButtons.get(0),
        1,
        SpringLayout.NORTH, breakpointButtonPanel);
    breakpointButtonPanel.add(breakpointButtons.get(0));
    breakpointButtonPanel.setVisible(true);

    JLayeredPane rowHeaderView = new JLayeredPane();
    rowHeaderView.setOpaque(true);
    SpringLayout headerLayout = new SpringLayout();
    rowHeaderView.setLayout(headerLayout);
    headerLayout.putConstraint(SpringLayout.WEST, lines,
        20,
        SpringLayout.WEST, rowHeaderView);
    headerLayout.putConstraint(SpringLayout.WEST, breakpointButtonPanel,
        0,
        SpringLayout.WEST, rowHeaderView);
    rowHeaderView.add(lines);
    lines.setOpaque(true);
    rowHeaderView.setForeground(Color.LIGHT_GRAY);
    rowHeaderView.add(breakpointButtonPanel);

    rowHeaderView.setPreferredSize(new Dimension(60, 100000000));
    codeScrollPane.setRowHeaderView(rowHeaderView);
    codeScrollPane.setPreferredSize(new Dimension(400, 300));
    codeScrollPane.setSize(400, 800);
    codePanel.add(codeScrollPane);


  }

  /**
   * initializes components of variable inspector.
   */
  private void initVariableInspector() {
    variableInspector = new JPanel();

    GroupLayout variableInspectorLayout = new GroupLayout(variableInspector);
    variableInspector.setLayout(variableInspectorLayout);
    String[] data = {"x = true", "y = ?", "count = 42"};
    listModel = new DefaultListModel<>();
    for (String s : data) {
      listModel.addElement(s);
    }
    variableInspectorList = new JList<>(listModel);
    variableInspectorList.setDragEnabled(true);
    variableInspectorList.setSelectionBackground(Color.YELLOW);
    variableInspectorList.setSelectionForeground(Color.BLACK);
    variableInspectorList.setFixedCellHeight(20);
    variableInspectorList.setToolTipText("Zelle markieren und mit Rechtsklick löschen");

    variableInspectorList.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
          listModel.remove(variableInspectorList.getSelectedIndex());
        }
        variableInspectorList.updateUI();
        variableInspectorScrollPane.updateUI();
      }

      @Override
      public void mousePressed(MouseEvent mouseEvent) {

      }

      @Override
      public void mouseReleased(MouseEvent mouseEvent) {

      }

      @Override
      public void mouseEntered(MouseEvent mouseEvent) {

      }

      @Override
      public void mouseExited(MouseEvent mouseEvent) {

      }
    });

    variableInspectorScrollPane = new JScrollPane();
    variableInspectorScrollPane
        .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    variableInspectorScrollPane.setPreferredSize(new Dimension(400, 200));
    variableInspectorScrollPane.setViewportView(variableInspectorList);

    JButton showHiddenVariables = new JButton("Ausgeblendete Variablen anzeigen");
    showHiddenVariables.addActionListener(actionEvent -> {
      listModel.clear();
      for (String s : data) {
        listModel.addElement(s);
      }
    });

    JLabel varLabel = new JLabel("Variableninspektor");

    variableInspectorLayout.setHorizontalGroup(
        variableInspectorLayout.createParallelGroup()
            .addComponent(varLabel)
            .addComponent(showHiddenVariables)
            .addComponent(variableInspectorScrollPane)
    );
    variableInspectorLayout.setVerticalGroup(
        variableInspectorLayout.createSequentialGroup()
            .addGroup(variableInspectorLayout.createSequentialGroup()
                .addComponent(varLabel)
                .addComponent(showHiddenVariables))
            .addComponent(variableInspectorScrollPane)
    );

  }

  /**
   * Getter-method for ID of the program panel.
   *
   * @return identifier
   */
  public String getId() {
    return id;
  }

  public void setText(String programText) {
    codeTextArea.setText(programText);
    listModel.removeAllElements();
    variableInspectorList.updateUI();
  }

  public void showVariables(List<String> variables) {
    listModel.removeAllElements();
    for (String s: variables) {
      listModel.addElement(s);
    }
  }

  public List<String> getInspectedVariables() {
    ArrayList<String> inspectedVariables = new ArrayList<>();
    for (int i = 0; i < listModel.size(); i++) {
      inspectedVariables.add(listModel.get(i));
    }
    return inspectedVariables;
  }

  public void showInput(String input) {
    inputvariableTextField.setText(input);
  }


}