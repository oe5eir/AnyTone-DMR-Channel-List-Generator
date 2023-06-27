package at.oe5eir.atdmr.channels;

/*
 *  Copyright (C) 2023 OE5EIR @ https://www.oe5eir.at/
 *
 *  Licensed under the GNU General Public License v3.0 (the "License").
 *  You may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *      https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import at.oe5eir.atdmr.channels.config.Channel;

import java.util.Comparator;

public class ChannelComparator implements Comparator<Channel> {
    @Override
    public int compare(Channel o1, Channel o2) {
        return o1.getChannelName().compareTo(o2.getChannelName());
    }
}
