---
title: $.util
---

$.util
===

## Overview

- Definition: [https://github.com/SAP/xsk/issues/16](https://github.com/SAP/xsk/issues/16)
- Module: [util/util.js](https://github.com/SAP/xsk/blob/main/modules/api/api-xsjs/src/main/resources/xsk/util/util.js)
- Status: `alpha`

## Sample Usage

```javascript
var util = $.util;
var randomID = util.createUuid(); // return random uuid

// Uint8Array
var arrayBuffer = [84,104,105,115,32,105,115,32,97,32,85,105,110,116,
                          56,65,114,114,97,121,32,99,111,110,118,101,114,116,
                          101,100,32,116,111,32,97,32,115,116,114,105,110,103];

var convertedBuff = util.stringify(arrayBuffer); // return converted arrayBuffer to String

var util = $.util;
var randomID = util.createUuid(); // return random uuid

let result = `\n randomID is : ${randomID} `;
result += `\n convertedBuff is: ${arrayBuffer} `;
$.response.setBody(result);
```

## Functions

| Members             | Description                                            | Returns     |
|---------------------|--------------------------------------------------------|-------------|
| **createUuid()**    | Returns a unique UUID.                                 | _`string`_  |
| **stringify(data)** | Recieves UintArray and return converted value.         | _`string`_  |