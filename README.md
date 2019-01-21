# MVPMinesweeper
Minesweeper with Model-View-Presenter architecture (Passive View).

## SOLID principles that have been applied and remarkable cases.
- **Single Responsibility Principle**: considered in all the design but this project can be slightly improved by dividing Presenter into two classes (BoardPresenter and StartMenuPresenter) to manage BoardDisplay and StartMenuDisplay respectively.

- **Open-Closed Principle**: beyond the possibility of extend public functionality or overwriting public methods, the Board implementation delegates much of the most critical functionality to anonymous implementations of Cell. It is possible to extend Board with a new implementation of Cell by overwriting the protected getCellImplementation(int row, int col) method which provides instances of an implementation of Cell, by default an anonymous class defined in this method.

- **Liskov Substitution Principle**: apart from what was explained about the implementation of Cell, the Presenter depends on the View interfaces, making it possible to replace implementations (see BoardDisplay and its fully replaceable swing implementation).

- **Interface Segregation Principle**: specially considered in the design of all interfaces.

- **Dependency Inversion Principle**: with the exception of the dependence of presenter with the implementation of Board (whose impact is reduced thanks to the open-closed principle), I have tried to design the presenter to depend exclusively on abstractions. Board also depends exclusively on abstractions (Cell and the listeners).

## About architecture

- Model is decoupled, it don't know nothing about the other parts, neither the Presenter nor View.
- Presenter is loose coupled to the View (only knows about interfaces, not implementations).
- View is decoupled from Model but coupled to Presenter.
