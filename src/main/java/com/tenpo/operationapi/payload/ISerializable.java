package com.tenpo.operationapi.payload;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ISerializable {
	String serializeMe() throws JsonProcessingException;
}
