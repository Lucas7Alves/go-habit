import React from "react";

export default function UserProfile({ name, avatar }) {
  return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", gap: "0.5rem" }}>
      <img 
        src={avatar} 
        alt="Foto de perfil" 
        style={{ width: 150, height: 150, objectFit: "cover", borderRadius: "0%",  }} 
      />
      <h2 style={{marginBottom:"70px",marginTop:"0px", padding:"0", color: "#4427AF"}}>{name}</h2>
    </div>
  );
}
