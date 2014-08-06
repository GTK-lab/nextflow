/*
 * Copyright (c) 2013-2014, Centre for Genomic Regulation (CRG).
 * Copyright (c) 2013-2014, Paolo Di Tommaso and the respective authors.
 *
 *   This file is part of 'Nextflow'.
 *
 *   Nextflow is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Nextflow is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Nextflow.  If not, see <http://www.gnu.org/licenses/>.
 */

package nextflow
import java.text.SimpleDateFormat
/**
 * Application main constants
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
class Const {

    /**
     * The application main package name
     */
    static final String MAIN_PACKAGE = Const.name.split('\\.')[0]

    /**
     * The application main name
     */
    static final String APP_NAME = MAIN_PACKAGE

    /**
     * The application home folder
     */
    static final File APP_HOME_DIR = getHomeDir(APP_NAME)

    /**
     * The application version
     */
    static final String APP_VER = "0.9.0"

    /**
     * The app build time as linux/unix timestamp
     */
    static final long APP_TIMESTAMP = 1407326619545

    /**
     * The app build number
     */
    static final int APP_BUILDNUM = 1956

    /**
     * The date time formatter string
     */
    static final DATETIME_FORMAT = 'dd-MM-yyyy HH:mm'

    /**
     * The app build time string relative to UTC timezone
     */
    static final String APP_TIMESTAMP_UTC = {

        def tz = TimeZone.getTimeZone('UTC')
        def fmt = new SimpleDateFormat(DATETIME_FORMAT)
        fmt.setTimeZone(tz)
        fmt.format(new Date(APP_TIMESTAMP)) + ' ' + tz.getDisplayName( true, TimeZone.SHORT )

    } ()


    /**
     * The app build time string relative to local timezone
     */
    static final String APP_TIMESTAMP_LOCAL = {

        def tz = TimeZone.getDefault()
        def fmt = new SimpleDateFormat(DATETIME_FORMAT)
        fmt.setTimeZone(tz)
        fmt.format(new Date(APP_TIMESTAMP)) + ' ' + tz.getDisplayName( true, TimeZone.SHORT )

    } ()

    static String deltaLocal() {
        def utc = APP_TIMESTAMP_UTC.split(' ')
        def loc = APP_TIMESTAMP_LOCAL.split(' ')

        if( APP_TIMESTAMP_UTC == APP_TIMESTAMP_LOCAL ) {
            return ''
        }

        def result = utc[0] == loc[0] ? loc[1,-1].join(' ') : loc.join(' ')
        return "($result)"
    }


    private static getHomeDir(String appname) {
        def home = System.getenv('NXF_HOME')
        def result = home ? new File(home) : new File( System.getProperty("user.home"), ".$appname" )

        if( !result.exists() && !result.mkdir() ) {
            throw new IllegalStateException("Cannot create path '${result}' -- check file system access permission")
        }

        return result
    }

    /*
     * The application 'logo'
     */

    static final String LOGO =

"""
      N E X T F L O W
      Version ${APP_VER} build ${APP_BUILDNUM}
      last modified ${APP_TIMESTAMP_UTC} ${deltaLocal()}
      http://nextflow.io
"""

}
