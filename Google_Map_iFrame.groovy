/**
 *  dashboard Google Map iFrame
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Special thanks goes to mbarone who created the original iFrame driver that the majority of this driver code as leveraged. 
 *  The original iframe code this driver is based on is available at https://raw.githubusercontent.com/michaelbarone/hubitat/master/drivers/iFrame.groovy
 *  
 *  This main purpose of this driver is to be able to create Hubitat rules that read the Latitude and Longitude attributes from other drivers and load into this driver so that
 *  a Google Map can be displayed where the device is located.
 *
 *
 *  Directions:  
 *     1   Create a virtual device with this driver.  
 *     2   Enter in the latitude and longitude coorindates for the Map you want to embed and/or create a Hubitat Rule to auotmatically update the latitude
 *         and longitude commands each time some othe device attributes change
 *     3   add this device to your dashboard, select the attribute and choose the "iFrame" attribute
 *
 *	   optional:
 *     4   update the css to style the iframe tile on the dashboard:
 *
 *		// replace '#tile-33' with this driver/device on your dashboard
 *	
 *		#tile-33 .tile-title {
 *			display:none;
 *		}
 *
 *		#tile-33 .tile-contents, #tile-33 .tile-primary{
 *			padding: 0;
 *			height:100%;
 *		}
 *
 *
 *
 *
 *
 *  Change History:
 *
 *    Date        Who            What
 *    ----        ---            ----
 *   6-19-22	gomce62  	Initial release 
 *   6-19-22    gomce62         Added parameter to enter type of map to display
 *   6-20-22    gomce62         Updated code for Map Type and added parameter for Map Zoom level and updated commands with code contributions from kahn-hubitat and Baz2473  
 * 	  
 */
        

preferences {
        input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true 
        input "mapType", "enum", title: "Select your desired map type", options: ["h":"Hybrid","k":"Satellite","n":"Normal"], defaultValue: "h"    
        input "MapZoomLevel", "number",range: "1..21" title: "Enter a value between 1-21 (default is 14) to set Zoom Level for Google Maps", defaultValue:14
}
metadata {
    definition (name: "Google Map iFrame", namespace: "gomce62", author: "gomce62", importUrl: "https://raw.githubusercontent.com/gomce62/Hubitat/Drivers/Google_Map_iFrame.groovy") {
        capability "Actuator"
        command "setLatitude", ["number"]
        command "setLongitude", ["number"]
        command "updateFrame"
              
        attribute "iFrame", "text"
        attribute "longitude", "number"
        attribute "latitude", "number"
                 
    }
}


def installed() {
	log.warn "installed..."
    sendEvent(name: "iFrame", value: "<div style='height: 100%; width: 100%'><iframe src='https://maps.google.com/maps?q=0,0&t=${mapType}&hl=es;z=${MapZoomLevel}&output=embed&' style='height: 100%; width:100%; border: none;'></iframe><div>")
    }

def setLatitude(lat)
{
   sendEvent(name: "latitude", value:lat)   
}

def setLongitude(lon)
{
   sendEvent(name: "longitude", value:lon)   
}

    def updateFrame() {
     
        def lon = device.currentValue('longitude')
        def lat = device.currentValue('latitude')
        
        
        if (lon == null) lon = 0.0
        if (lat == null) lat = 0.0
        if (MapZoomLevel == null) MapZoomLevel = 14      

        
        log.debug "current long = $lon"
        log.debug "current lat = $lat"
        
        sendEvent(name: "iFrame", value: "<div style='height: 100%; width: 100%'><iframe src='https://maps.google.com/maps?q=${lat},${lon}&hl=en&z=${MapZoomLevel}&t=${mapType}&output=embed&' style='height: 100%; width:100%; frameborder:0 marginheight:0 marginwidth:0 border: none;'></iframe><div>")
        
        
    }   
