import { BrowserRouter, Routes } from "react-router-dom";
import { Route } from "react-router";
import SendPage from "./pages/SendPage";
import NotFoundPage from "./pages/NotFoundPage";
import { createTheme, ThemeProvider } from "@mui/material";

function App() {
  const theme = createTheme({
    palette: {
      primary: {
        main: "#0046FF",
      },
      secondary: {
        main: "#000000"
      }
    },
  });

  return (
    <ThemeProvider theme={theme}>
      <BrowserRouter basename="remit">
        <Routes>
          <Route path="/a" element={<SendPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
