package main;

import entidad.Entidad;


public class ColisionJugador {

    // Referencia al juego principal para acceder a tiles y objetos
    Juego gp;

    // Constructor que recibe la instancia del juego
    public ColisionJugador(Juego gp) {
        this.gp = gp;
    }

    /**
     * Método que verifica la colisión entre una entidad y los tiles del mapa.
     * Si la entidad intenta moverse hacia un tile colisionable, se activa la colisión.
     */
    public void colisiontiles(Entidad entity) {
        // Calcula los límites del área de colisión de la entidad
        int entidadleftX = entity.mundox + entity.soidArea.x;
        int entidadrightX = entity.mundox + entity.soidArea.x + entity.soidArea.width;
        int entidadtopY = entity.mundoy + entity.soidArea.y;
        int entidadbottomY = entity.mundoy + entity.soidArea.y + entity.soidArea.height;

        // Calcula la posición de la entidad en la cuadrícula del mapa
        int entidadLeftCol = entidadleftX / gp.total;
        int entidadrightcol = entidadrightX / gp.total;
        int entidadTopRow = entidadtopY / gp.total;
        int entidadbottomrow = entidadbottomY / gp.total;

        int tilenumero1, tilenumero2; // Números de los tiles que se revisarán

        // Verifica colisión según la dirección de la entidad
        switch (entity.direccion) {
            case "up":
                entidadTopRow = (entidadtopY - entity.velocidad) / gp.total;
                tilenumero1 = gp.tilem.maptilenum[entidadLeftCol][entidadTopRow];
                tilenumero2 = gp.tilem.maptilenum[entidadrightcol][entidadTopRow];
                if (gp.tilem.tiles[tilenumero1].colisionable || gp.tilem.tiles[tilenumero2].colisionable) {
                    entity.colisionOn = true; // Activa la colisión
                }
                break;

            case "down":
                entidadbottomrow = (entidadbottomY + entity.velocidad) / gp.total;
                tilenumero1 = gp.tilem.maptilenum[entidadLeftCol][entidadbottomrow];
                tilenumero2 = gp.tilem.maptilenum[entidadrightcol][entidadbottomrow];
                if (gp.tilem.tiles[tilenumero1].colisionable || gp.tilem.tiles[tilenumero2].colisionable) {
                    entity.colisionOn = true;
                }
                break;

            case "left":
                entidadLeftCol = (entidadleftX - entity.velocidad) / gp.total;
                tilenumero1 = gp.tilem.maptilenum[entidadLeftCol][entidadTopRow];
                tilenumero2 = gp.tilem.maptilenum[entidadLeftCol][entidadbottomrow];
                if (gp.tilem.tiles[tilenumero1].colisionable || gp.tilem.tiles[tilenumero2].colisionable) {
                    entity.colisionOn = true;
                }
                break;

            case "right":
                entidadrightcol = (entidadrightX + entity.velocidad) / gp.total;
                tilenumero1 = gp.tilem.maptilenum[entidadrightcol][entidadTopRow];
                tilenumero2 = gp.tilem.maptilenum[entidadrightcol][entidadbottomrow];
                if (gp.tilem.tiles[tilenumero1].colisionable || gp.tilem.tiles[tilenumero2].colisionable) {
                    entity.colisionOn = true;
                }
                break;
        }
    }


    public int checkObject(Entidad entity, boolean jugador) {
        int index = 999; // Valor predeterminado si no hay colisión

        // Recorre todos los objetos del juego
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // Obtiene las coordenadas de colisión de la entidad
                entity.soidArea.x = entity.mundox + entity.soidArea.x;
                entity.soidArea.y = entity.mundoy + entity.soidArea.y;

                // Obtiene las coordenadas de colisión del objeto
                gp.obj[i].solidarea.x = gp.obj[i].mundox + gp.obj[i].solidarea.x;
                gp.obj[i].solidarea.y = gp.obj[i].mundoy + gp.obj[i].solidarea.y;

                // Verifica colisión según la dirección de la entidad
                switch (entity.direccion) {
                    case "up":
                        entity.soidArea.y -= entity.velocidad;
                        if (entity.soidArea.intersects(gp.obj[i].solidarea)) {
                            if (gp.obj[i].colision) {
                                entity.colisionOn = true;
                            }
                            if (jugador) {
                                index = i; // Guarda el índice del objeto
                            }
                        }
                        break;

                    case "down":
                        entity.soidArea.y += entity.velocidad;
                        if (entity.soidArea.intersects(gp.obj[i].solidarea)) {
                            if (gp.obj[i].colision) {
                                entity.colisionOn = true;
                            }
                            if (jugador) {
                                index = i;
                            }
                        }
                        break;

                    case "left":
                        entity.soidArea.x -= entity.velocidad;
                        if (entity.soidArea.intersects(gp.obj[i].solidarea)) {
                            if (gp.obj[i].colision) {
                                entity.colisionOn = true;
                            }
                            if (jugador) {
                                index = i;
                            }
                        }
                        break;

                    case "right":
                        entity.soidArea.x += entity.velocidad;
                        if (entity.soidArea.intersects(gp.obj[i].solidarea)) {
                            if (gp.obj[i].colision) {
                                entity.colisionOn = true;
                            }
                            if (jugador) {
                                index = i;
                            }
                        }
                        break;
                }

                // Restablece las coordenadas originales después de la verificación
                entity.soidArea.x = entity.solidareaX;
                entity.soidArea.y = entity.solidareay;
                gp.obj[i].solidarea.x = gp.obj[i].solidareax;
                gp.obj[i].solidarea.y = gp.obj[i].solidareay;
            }
        }
        return index; // Retorna el índice del objeto colisionado o 999 si no hay colisión
    }
}
