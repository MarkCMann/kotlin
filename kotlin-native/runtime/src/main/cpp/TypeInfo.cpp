/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "KAssert.h"
#include "TypeInfo.h"
#include "Memory.h"
#include "Types.h"
#include "KString.h"

#include <exception>
#include <iostream>

extern "C" {

// Seeks for the specified id. In case of failure returns a valid pointer to some record, never returns nullptr.
// It is the caller's responsibility to check if the search has succeeded or not.
RUNTIME_NOTHROW InterfaceTableRecord const* LookupInterfaceTableRecord(TypeInfo const* typeInfo, int interfaceTableSize, ClassId interfaceId) {
  InterfaceTableRecord const* interfaceTable = typeInfo->interfaceTable_;
  if (interfaceTableSize <= 8) {
    // Linear search.
    int i;
    for (i = 0; i < interfaceTableSize - 1 && interfaceTable[i].id < interfaceId; ++i) {}
    return interfaceTable + i;
  }
  int l = 0, r = interfaceTableSize - 1;
  while (l < r) {
    int m = (l + r) / 2;
    if (interfaceTable[m].id < interfaceId)
      l = m + 1;
    else r = m;
  }
  return interfaceTable + l;
}

RUNTIME_NOTHROW VTableElement LookupInterfaceMethodVTableRecord(TypeInfo const* typeInfo, InterfaceTableRecord const* interfaceRecord, int methodIndex, ClassId interfaceId) {
  RuntimeAssert(interfaceRecord != nullptr, "Dereferencing nullptr to interface record.");
  RuntimeAssert(interfaceRecord->id == interfaceId, "Dereferencing pointer to interface record that doesn't match interface id");
  return interfaceRecord->vtable[methodIndex];
}

RUNTIME_NOTHROW int Kotlin_internal_reflect_getObjectReferenceFieldsCount(ObjHeader* object) {
    auto *info = object->type_info();
    if (info->IsArray()) return 0;
    return info->objOffsetsCount_;
}

RUNTIME_NOTHROW OBJ_GETTER(Kotlin_internal_reflect_getObjectReferenceFieldByIndex, ObjHeader* object, int index) {
    RETURN_OBJ(*reinterpret_cast<ObjHeader**>(reinterpret_cast<uintptr_t>(object) + object->type_info()->objOffsets_[index]));
}

RUNTIME_NOTHROW InterfaceTableRecord const* SanitizedInterfaceTableRecord(TypeInfo const* typeInfo, int interfaceTableIndex) {
  RuntimeAssert(typeInfo->interfaceTable_ != nullptr, "Interface table is null");
  RuntimeAssert(interfaceTableIndex < typeInfo->interfaceTableSize_, "Interface table index out of range.");
  return (typeInfo->interfaceTable_ + interfaceTableIndex);
}

}
