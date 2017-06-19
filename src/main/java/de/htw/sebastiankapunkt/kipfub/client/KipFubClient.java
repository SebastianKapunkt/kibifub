package de.htw.sebastiankapunkt.kipfub.client;

import lenz.htw.kipifub.ColorChange;
import lenz.htw.kipifub.net.NetworkClient;

public class KipFubClient {

    public KipFubClient(String host, String name) {
        int x = 512;
        int y = 512;

        NetworkClient networkClient = new NetworkClient(host, name);
        networkClient.getMyPlayerNumber(); // 0 = rot, 1 = grün, 2 = blau

        int rgb = networkClient.getBoard(x, y); // 0-1023 ->
        int b = rgb & 255;
        int g = (rgb >> 8) & 255;
        int r = (rgb >> 16) & 255;

        networkClient.getInfluenceRadiusForBot(0); // -> 40

        networkClient.getScore(0); // Punkte von rot

        networkClient.isWalkable(x, y); // begehbar oder Hinderniss?

        networkClient.setMoveDirection(0, 1, 0); // bot 0 nach rechts
        networkClient.setMoveDirection(1, 0.23f, -0.52f); // bot 1 nach rechts unten

        ColorChange colorChange;
        while ((colorChange = networkClient.pullNextColorChange()) != null) {
            //verarbeiten von colorChange
            //colorChange.player, colorChange.bot, colorChange.x, colorChange.y
        }
    }

}
