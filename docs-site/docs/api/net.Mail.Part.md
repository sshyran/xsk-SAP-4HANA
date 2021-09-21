---
title: $.net.Mail.Part
---

$.net.Mail.Part
===

`$.net.Mail.Part` class for constructing email parts.

## Overview

- Definition: [https://github.com/SAP/xsk/issues/19](https://github.com/SAP/xsk/issues/19)
- Module: [net/net.js](https://github.com/SAP/xsk/tree/main/modules/api/api-xsjs/src/main/resources/xsk/net/net.js)
- Status: `alpha`

## Sample Usage

```javascript
// Create an inline $.net.Mail.Part from JSObject.
var firstPart = new $.net.Mail.Part();
firstPart.type = $.net.Mail.Part.TYPE_INLINE;
firstPart.data = data1; // data1 contains the binary data of the image
firstPart.contentType = "image/jpg";
firstPart.contentId = "IMAGE1_ID";
firstPart.fileName = "fileName1.jpg";
firstPart.fileNameEncoding = "UTF-8";

// Create an attachment $.net.Mail.Part from JSObject.
var secondPart = new $.net.Mail.Part({
    type: $.net.Mail.Part.TYPE_ATTACHMENT,
    data: data2, // data2 contains the binary data of the image
    contentType: "image/jpg",
    fileName: "fileName2.jpg",
    fileNameEncoding: "UTF-8"
});

// Create a text $.net.Mail.Part object.
var thirdPart = new $.net.Mail.Part();
thirdPart.type = $.net.Mail.Part.TYPE_TEXT;
thirdPart.text = "<html><head></head><body><img src=\"cid:IMAGE1_ID\"></body></html>";
thirdPart.contentType = "text/html";
thirdPart.alternative = "The alternative of the text";
thirdPart.alternativeContentType = "text/plain";
thirdPart.encoding = "UTF-8";

// Create an $.net.Mail object.
var mail = new $.net.Mail({
    sender: {address: "sender@sap.com"},
    to: [{name: "to1", address: "to1@sap.com"}, {name: "to2", address: "to2@sap.com"}],
    cc: [{ name: "cc1", address: "cc1@sap.com"}, { name: "cc2", address: "cc2@sap.com"}],
    bcc: [{ name: "bcc1", address: "bcc1@sap.com"}],
    subject: "subject",
    subjectEncoding: "UTF-8"
});

mail.parts.push(firstPart, secondPart, thirdPart);
mail.send();
```

## Constructors

```javascript
new $.net.Mail.Part(PartObject)
```

## Parameters

| Parameter Name     | Description                                             | Required     | Type       |
|--------------------|---------------------------------------------------------|--------------|------------|
| **PartObject**     | JS object containing elements of a Part in JSON format. | _`optional`_ | _`object`_ |

## Properties

| Name                       | Description                                                     | Type               |
|----------------------------|-----------------------------------------------------------------|--------------------|
| **alternative**            | Property used for initializing "alternative" property of the text $.net.Mail.Part object. | _`string`_             |
| **alternativeContentType** | Property used for initializing "alternativeContentType" property of the text $.net.Mail.Part object. If this property is not set, the default value is "text/plain". | _`string`_             |
| **contentId**              | Property used for initializing "contentId" property of the inline $.net.Mail.Part object. | _`string`_             |
| **contentType**            | Property used for initializing "contentType" property of the $.net.Mail.Part object. | _`string`_             |
| **data**                   | Property used for initializing "data" property of the attachment and inline $.net.Mail.Part object. | _`string/ArrayBuffer`_ |
| **encoding**               | Property used for initializing "encoding" property of the text $.net.Mail.Part object. It also applies to alternative text. If this property is not set, the default value is "UTF-8". | _`string`_             |
| **fileName**               | Property used for initializing "fileName" property of the attachment and inline $.net.Mail.Part object. It contains the full name of the file with the extension, example "file.txt". | _`string`_             |
| **fileNameEncoding**       | Property used for initializing "fileNameEncoding" property of the attachment and inline $.net.Mail.Part object. It is the encoding of the filename. If this property is not set, the default value is "UTF-8". | _`string`_             |
| **text**                   | Property used for initializing "text" property of the text $.net.Mail.Part object. | _`string`_             |
| **type**                   | Property used for initializing "type" property of the $.net.Mail.Part object. If this property is not set, the part will not be set. It should be one of the following: $.net.Mail.Part.TYPE_TEXT $.net.Mail.Part.TYPE_ATTACHMENT $.net.Mail.Part.TYPE_INLINE | _`string`_             |