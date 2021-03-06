package de.htw.sebastiankapunkt.kipfub.game;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.NUMBER;
import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;


public class GameField {

    private ScaledField[][] game = new ScaledField[NUMBER][NUMBER];

    private int myPlayerNumber;
    private PublishSubject<ScaledField> subject = PublishSubject.create();

    public GameField(int playerNumber) {
        myPlayerNumber = playerNumber;
    }

    public void observe(PublishSubject<ColorChange> connect) {
        connect.subscribe(new DisposableObserver<ColorChange>() {
            @Override
            public void onNext(ColorChange colorChange) {
                ScaledField up = null;
                ScaledField right = null;
                ScaledField down = null;
                ScaledField left = null;

                ScaledField changes = getField(colorChange.x, colorChange.y);

                switch (colorChange.bot) {
                    case 0:
                        if (colorChange.y - 16 > 0) {
                            up = getField(colorChange.x, colorChange.y - SCALE);
                        }
                        if (colorChange.x + 16 < 1024) {
                            right = getField(colorChange.x + SCALE, colorChange.y);
                        }
                        if (colorChange.y + 16 < 1024) {
                            down = getField(colorChange.x, colorChange.y - SCALE);
                        }
                        if (colorChange.x - 16 > 0) {
                            left = getField(colorChange.x - SCALE, colorChange.y);
                        }
                        if (up != null) {
                            score(colorChange, up, 0.5f);
                        }
                        if (right != null) {
                            score(colorChange, right, 0.5f);
                        }
                        if (down != null) {
                            score(colorChange, down, 0.5f);
                        }
                        if (left != null) {
                            score(colorChange, left, 0.5f);
                        }
                        score(colorChange, changes, 0.5f);
                        break;
                    case 1:
                        score(colorChange, changes, 2);
                        break;
                    case 2:
                        if (colorChange.y - 16 > 0) {
                            up = getField(colorChange.x, colorChange.y - SCALE);
                        }
                        if (colorChange.x + 16 < 1024) {
                            right = getField(colorChange.x + SCALE, colorChange.y);
                        }
                        if (colorChange.y + 16 < 1024) {
                            down = getField(colorChange.x, colorChange.y - SCALE);
                        }
                        if (colorChange.x - 16 > 0) {
                            left = getField(colorChange.x - SCALE, colorChange.y);
                        }
                        if (up != null) {
                            score(colorChange, up, 2);
                        }
                        if (right != null) {
                            score(colorChange, right, 2);
                        }
                        if (down != null) {
                            score(colorChange, down, 2);
                        }
                        if (left != null) {
                            score(colorChange, left, 2);
                        }
                        score(colorChange, changes, 2);
                        break;
                    default:
                        score(colorChange, changes, 2);
                }

                subject.onNext(changes);

                if (up != null) {
                    subject.onNext(up);
                }
                if (right != null) {
                    subject.onNext(right);
                }
                if (down != null) {
                    subject.onNext(down);
                }
                if (left != null) {
                    subject.onNext(left);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Game onCompleted called");
            }
        });
    }

    private void score(ColorChange colorChange, ScaledField changes, float value) {
        if (colorChange.player == myPlayerNumber) {
            changes.addScore(value);
        } else {
            changes.removeScore(value);
        }
    }

    public PublishSubject<ScaledField> connect() {
        return subject;
    }

    private ScaledField getField(int x, int y) {
        return game[x / SCALE][y / SCALE];
    }

    public ScaledField[][] getBoard() {
        return game;
    }

    public ScaledField createField(int x, int y, NetworkClient client) {
        ScaledField scaledField = fillScaledField(x * SCALE, y * SCALE, client);
        game[x][y] = scaledField;
        return scaledField;
    }

    private ScaledField fillScaledField(int xStart, int yStart, NetworkClient client) {
        for (int xZoomed = xStart; xZoomed < xStart + SCALE; xZoomed++) {
            for (int yZoomed = yStart; yZoomed < yStart + SCALE; yZoomed++) {
                if (!client.isWalkable(xZoomed, yZoomed)) {
                    return new ScaledField(xStart, yStart, false);
                }
            }
        }
        return new ScaledField(xStart, yStart, true);
    }
}
