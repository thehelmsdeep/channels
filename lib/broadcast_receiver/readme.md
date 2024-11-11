A BroadcastReceiver in Android allows your app to
listen for and respond to broadcast messages from other applications or the system itself.




There are many predefined actions for broadcasts in Android.
These broadcast actions cover a wide range of system events,
such as network changes, time changes, battery status,
and many other events.


1. Listen to System-Wide Events
   You can listen to various system broadcasts related to the device's state or status changes:

1. System Events & States:
   Intent.ACTION_AIRPLANE_MODE_CHANGED: Sent when the airplane mode state changes.
   Intent.ACTION_BATTERY_CHANGED: Sent when the battery's status, level, or charging state changes.
   Intent.ACTION_BATTERY_LOW: Sent when the battery is low.
   Intent.ACTION_BATTERY_OKAY: Sent when the battery has returned to an acceptable level.
   Intent.ACTION_BOOT_COMPLETED: Sent after the system has finished booting.
   Intent.ACTION_CONFIGURATION_CHANGED: Sent when the device's configuration changes (e.g., locale or screen size).
   Intent.ACTION_DATE_CHANGED: Sent when the device’s date changes.
   Intent.ACTION_POWER_CONNECTED: Sent when the device is connected to a power source.
   Intent.ACTION_POWER_DISCONNECTED: Sent when the device is disconnected from a power source.
   Intent.ACTION_SCREEN_OFF: Sent when the screen turns off.
   Intent.ACTION_SCREEN_ON: Sent when the screen turns on.
   Intent.ACTION_SHUTDOWN: Sent when the device is shutting down.
2. Time & Date:
   Intent.ACTION_TIME_CHANGED: Sent when the time has been manually set by the user.
   Intent.ACTION_TIME_TICK: Sent every minute to notify that one minute has passed. This is commonly used to perform tasks periodically.
   Intent.ACTION_TIMEZONE_CHANGED: Sent when the device's time zone changes.
3. Connectivity & Network:
   Intent.ACTION_CONNECTIVITY_CHANGED: Sent when network connectivity changes.
   Intent.ACTION_WIFI_STATE_CHANGED: Sent when the Wi-Fi state changes.
   Intent.ACTION_WIFI_CALLING_STATUS_CHANGED: Sent when the Wi-Fi calling status changes.
   Intent.ACTION_DATA_CONNECTIVITY_CHANGED: Sent when the mobile data connectivity state changes.
   Intent.ACTION_ETHERNET_STATE_CHANGED: Sent when Ethernet state changes.
4. Package & Application Events:
   Intent.ACTION_PACKAGE_ADDED: Sent when an application is installed on the device.
   Intent.ACTION_PACKAGE_CHANGED: Sent when an application is updated or modified.
   Intent.ACTION_PACKAGE_REPLACED: Sent when an application is replaced (e.g., after an update).
   Intent.ACTION_PACKAGE_REMOVED: Sent when an application is uninstalled or removed.
   Intent.ACTION_PACKAGE_STARTED: Sent when an app is started (not commonly used).
   Intent.ACTION_PACKAGE_STOPPED: Sent when an app is stopped (not commonly used).
   Intent.ACTION_PACKAGE_DATA_CLEARED: Sent when an app’s data is cleared.
5. Media & Storage:
   Intent.ACTION_MEDIA_BUTTON: Sent when the user presses a hardware media button (e.g., play, pause).
   Intent.ACTION_MEDIA_EJECT: Sent when a removable media has been ejected.
   Intent.ACTION_MEDIA_MOUNTED: Sent when external storage is mounted.
   Intent.ACTION_MEDIA_SCANNER_SCAN_FILE: Sent to initiate a scan of a media file.
   Intent.ACTION_MEDIA_SCANNER_FINISHED: Sent when media scanning is complete.
6. User Interaction Events:
   Intent.ACTION_HEADSET_PLUG: Sent when the state of the wired headset changes (plugged in or unplugged).
   Intent.ACTION_SCREEN_OFF: Sent when the screen is turned off (device locked).
   Intent.ACTION_SCREEN_ON: Sent when the screen is turned on.
   Intent.ACTION_USER_PRESENT: Sent when the device is unlocked by the user.
7. Location Events:
   Intent.ACTION_LOCATION_SOURCE_SETTINGS: Sent when the location settings are changed.
   Intent.ACTION_GPS_ENABLED: Sent when GPS is enabled.
8. Device Events:
   Intent.ACTION_DEVICE_STORAGE_LOW: Sent when the device storage is running low.
   Intent.ACTION_DEVICE_STORAGE_OK: Sent when the device storage has returned to a safe level.
9. Other Events:
   Intent.ACTION_INPUT_METHOD_CHANGED: Sent when the input method (keyboard) has been changed.
   Intent.ACTION_LOCALE_CHANGED: Sent when the device’s locale (language) has changed.
   Intent.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION: Sent when the user is




2. Listen to System-Wide Events
   You can listen to various system broadcasts related to the device's state or status changes:

Battery and Charging:

ACTION_BATTERY_LOW: When the battery is low.
ACTION_BATTERY_OKAY: When the battery status returns to normal after being low.
ACTION_POWER_CONNECTED: When the device is plugged into a power source (charging).
ACTION_POWER_DISCONNECTED: When the device is unplugged from the charger.
ACTION_BATTERY_CHANGED: When the battery level or status changes (e.g., charging, discharging).
Network Connectivity:

CONNECTIVITY_ACTION: When the network state changes (Wi-Fi, mobile data).
WIFI_STATE_CHANGED: When the Wi-Fi state changes (enabled, disabled, etc.).
NETWORK_STATE_CHANGED: When the network state changes, e.g., from mobile data to Wi-Fi.
Time and Date:

TIME_TICK: When the time has changed (on a 1-minute interval).
TIMEZONE_CHANGED: When the device's time zone changes.
ALARM_ALERT: When the alarm goes off.
Device Boot and Shutdown:

BOOT_COMPLETED: When the device finishes booting up (requires permission).
ACTION_SHUTDOWN: When the device is shutting down.
Airplane Mode:

AIRPLANE_MODE: When airplane mode is toggled on or off.
Device Screen:

SCREEN_ON: When the screen is turned on.
SCREEN_OFF: When the screen is turned off.
Charging State and Battery Health:

ACTION_BATTERY_CHANGED: Triggered when the battery state changes (level, charging status).
ACTION_BATTERY_HEALTH_CHANGED: When the health of the battery changes.
Bluetooth:

BluetoothAdapter.ACTION_STATE_CHANGED: When the Bluetooth adapter state changes.
BluetoothDevice.ACTION_PAIRING_REQUEST: When a pairing request is made.
Location:

LOCATION_MODE_CHANGED: When the location mode changes.
PROVIDERS_CHANGED: When location providers (GPS, network, etc.) change.



3. Listen to App-Specific Events
   You can listen for events that are specific to app installation, uninstallation, or changes:

App Installation/Uninstallation:

PACKAGE_ADDED: When a new app is installed.
PACKAGE_REMOVED: When an app is uninstalled.
PACKAGE_CHANGED: When an app is updated or its data is modified.
App Updates:

PACKAGE_REPLACED: When an app is updated with a new version.
App Permissions:

PERMISSIONS_CHANGED: When app permissions change.

4. Custom Broadcasts Between Apps
5. React to External Media (e.g., USB, SD card)
6. Handle Alarm and Timer Events
7.  Monitor System and Hardware Changes
8. Handle External Notifications
9. Security and Permissions
10. React to User Interactions
11.  Monitor and Control System Services
12. Respond to Bluetooth and Wireless Changes
13. Permissions and Security

