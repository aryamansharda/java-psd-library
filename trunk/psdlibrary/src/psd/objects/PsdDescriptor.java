/*
 * This file is part of java-psd-library.
 * 
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package psd.objects;

import java.io.IOException;
import java.util.*;

import psd.PsdInputStream;

/**
 * 
 * @author Dmitry Belsky
 */
public class PsdDescriptor extends PsdObject {

	private final String classId;
	private final HashMap<String, PsdObject> objects = new HashMap<String, PsdObject>();

	public PsdDescriptor(PsdInputStream stream) throws IOException {
		// Unicode string: name from classID
		int nameLen = stream.readInt() * 2;
		stream.skipBytes(nameLen);

		classId = stream.readPsdString();
		int itemsCount = stream.readInt();
		logger.finest("PsdDescriptor.itemsCount: " + itemsCount);
		for (int i = 0; i < itemsCount; i++) {
			String key = stream.readPsdString();
			logger.finest("PsdDescriptor.key: " + key);
			objects.put(key, PsdObject.loadPsdObject(stream));
		}
	}

	public String getClassId() {
		return classId;
	}

	public Map<String, PsdObject> getObjects() {
		return objects;
	}

	public PsdObject get(String key) {
		return objects.get(key);
	}

	public boolean containsKey(String key) {
		return objects.containsKey(key);
	}

	@Override
	public String toString() {
		return "Objc:" + objects.toString();
	}

}