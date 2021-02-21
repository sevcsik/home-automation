let nixpkgs = fetchTarball
  # Csak az unstable-en van olyan chromedriver, ami passzol a chromiumhoz
  https://releases.nixos.org/nixos/unstable/nixos-21.03pre257780.e9158eca70a/nixexprs.tar.xz
  ; pkgs = import nixpkgs {}
  ; in
    pkgs.mkShell { buildInputs = with pkgs; [ jdk14 leiningen ]
                 ; }
