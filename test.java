import React, { useState } from 'react';
import xmlCrypto from 'xml-crypto';

const SamlDecoder = () => {
  const [samlRequest, setSamlRequest] = useState('');
  const [decodedValue, setDecodedValue] = useState('');

  const decodeSamlRequest = () => {
    try {
      const doc = new xmlCrypto().parsing(samlRequest);
      const base64Request = doc.getElementsByTagName('samlp:AuthnRequest')[0].textContent;
      const decodedRequest = Buffer.from(base64Request, 'base64').toString('utf-8');
      setDecodedValue(decodedRequest);
    } catch (error) {
      console.error('Error decoding SAML Request:', error.message);
      setDecodedValue('Error decoding SAML Request. Please check the input.');
    }
  };

  return (
    <div>
      <h2>SAML Request Decoder</h2>
      <textarea
        placeholder="Paste your SAML Request here"
        value={samlRequest}
        onChange={(e) => setSamlRequest(e.target.value)}
      />
      <br />
      <button onClick={decodeSamlRequest}>Decode</button>
      <br />
      <h3>Decoded Value:</h3>
      <pre>{decodedValue}</pre>
    </div>
  );
};

export default SamlDecoder;
