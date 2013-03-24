/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtuesimerkki;

import java.util.List;

/**
 *
 * @author vito
 */
public interface Reader {
    List<Player> getPlayers();
    int extractInt(String str);
}
