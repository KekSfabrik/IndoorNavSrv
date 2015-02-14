/**
 * The Package with all neccessary types for all layers: The Database conforming
 * classes {@link de.hsmainz.gi.types.Beacon}, {@link de.hsmainz.gi.types.Site}, 
 * {@link de.hsmainz.gi.types.Location} and its Primary Key 
 * {@link de.hsmainz.gi.types.LocationId} aswell as the Exchange Types allowing 
 * deserialization of {@link com.vividsolutions.jts.geom.Point} 
 * ({@link de.hsmainz.gi.types.WkbPoint}) and 
 * {@link de.hsmainz.gi.types.Location} ({@link de.hsmainz.gi.types.WkbLocation}
 * which holds a WkbPoint instead of a Point). All Classes implement the 
 * {@link de.hsmainz.gi.types.IndoorNavEntity} interfaces.
 * 
 * @version 1.0
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
package de.hsmainz.gi.types;
