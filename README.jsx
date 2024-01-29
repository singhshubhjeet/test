import React, { useState } from 'react';

interface TokenDecoderProps {
  jwtToken: string;
}

const TokenDecoder: React.FC<TokenDecoderProps> = ({ jwtToken }) => {
  const [decodedToken, setDecodedToken] = useState<string | null>(null);

  const decodeToken = () => {
    try {
      const tokenSegments = jwtToken.split('.');
      if (tokenSegments.length === 3) {
        const decodedPayload = atob(tokenSegments[1]);
        setDecodedToken(JSON.parse(decodedPayload));
      } else {
        setDecodedToken(null);
      }
    } catch (error) {
      console.error('Error decoding token:', error.message);
      setDecodedToken(null);
    }
  };

  return (
    <div className="token-decoder">
      <h2>JWT Token Decoder</h2>
      <textarea
        placeholder="Enter JWT Token"
        value={jwtToken}
        onChange={(e) => setDecodedToken(null)}
      />
      <button onClick={decodeToken}>Decode Token</button>
      {decodedToken && (
        <div className="decoded-result">
          <h3>Decoded Value:</h3>
          <pre>{JSON.stringify(decodedToken, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default TokenDecoder;

