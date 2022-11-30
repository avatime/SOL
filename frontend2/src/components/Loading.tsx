import { Box } from "@mui/material";

export default function Loading() {
  return (
    <Box display="flex" flexDirection="column" alignItems="center" height="100vh">
      <Box mt={10}>
        <img src="/favicon.ico" />
      </Box>
    </Box>
  );
}
