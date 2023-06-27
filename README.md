# Channel List Generator for AnyTone DMR Radios

This application creates the channel list based on the OEVSV database for AnyTone radios which can be imported to the radio via the CPS.
It generates a channel list with all Austrian FM and DMR repeaters.

## Requirements
This program needs the Java Runtime Environment 8 to run. \
If you want to change/recompile the program then using the IntelliJ IDE is recommended.

## Run the application
Open a shell on your machine and navigate to the folder containing the downloaded or self-created JAR file.
Then run: `java -jar anytone-dmr-channellist-generator.jar [OUTPUT FILE] [CALLSIGN]`

## Import into CPS

First you need to create a Radio ID in the Radio ID List with your DMR ID and your callsign (as entered when running the program).

Also, you must create a talk group in the "Contact/Talk Group" list with the following parameters:
 - Name: 232-Austria
 - Call Type: Group Call
 - TG/DMR ID: 232
 - Call Alert: None

You can change this talk group later, but it must exist for the import as it is the default talk group for all DMR channels!

## License

Copyright (C) 2023 OE5EIR @ https://www.oe5eir.at/

Licensed under the GNU General Public License v3.0 (the "License"). \
You may not use this project except in compliance with the License.

You may obtain a copy of the License at: \
https://www.gnu.org/licenses/gpl-3.0.en.html

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
