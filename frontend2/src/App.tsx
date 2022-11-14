import { BrowserRouter, Routes } from "react-router-dom";
import { Route } from "react-router";
import SendPage from "./pages/SendPage";
import ExpirationTokenPage from "./pages/ExpirationTokenPage";
import { createTheme, ThemeProvider } from "@mui/material";
import PrivacyPage from "./pages/PrivacyPage";
import SuccessPage from "./pages/SuccessPage";
import NotFoundPage from "./pages/NotFoundPage";

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
      <BrowserRouter>
        <Routes>
          <Route path="/remit" element={<SendPage />} />
          <Route path="/privacy" element={<PrivacyPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
