const form = document.querySelector("#shorten-form");
const input = document.querySelector("#long-url");
const result = document.querySelector("#result");
const message = document.querySelector("#message");
const button = form.querySelector("button");

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  result.hidden = true;
  result.replaceChildren();
  message.textContent = "";

  let parsedUrl;
  try {
    parsedUrl = new URL(input.value.trim());
  } catch {
    message.textContent = "Enter a valid URL.";
    input.focus();
    return;
  }

  if (!["http:", "https:"].includes(parsedUrl.protocol) || !parsedUrl.hostname) {
    message.textContent = "Only valid HTTP and HTTPS URLs are supported.";
    input.focus();
    return;
  }

  button.disabled = true;
  button.textContent = "Creating link…";

  try {
    const response = await fetch("/api/v1/url/shorten", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ longUrl: parsedUrl.href })
    });

    const data = await response.json().catch(() => ({}));
    if (!response.ok) {
      throw new Error(data.message || "Unable to shorten this URL. Please try again.");
    }

    if (!data.shortUrl) {
      throw new Error("The server returned an invalid response.");
    }

    const heading = document.createElement("strong");
    heading.textContent = "Your shortened URL";

    const link = document.createElement("a");
    link.href = data.shortUrl;
    link.textContent = data.shortUrl;
    link.target = "_blank";
    link.rel = "noopener noreferrer";

    const copyButton = document.createElement("button");
    copyButton.type = "button";
    copyButton.className = "copy-button";
    copyButton.textContent = "Copy";
    copyButton.addEventListener("click", async () => {
      try {
        await navigator.clipboard.writeText(data.shortUrl);
        copyButton.textContent = "Copied!";
      } catch {
        message.textContent = "Copy failed. Select and copy the link manually.";
      }
    });

    result.append(heading, link, copyButton);
    result.hidden = false;
  } catch (error) {
    message.textContent = error.message || "Could not connect to Shortly. Please try again.";
  } finally {
    button.disabled = false;
    button.innerHTML = 'Shorten URL <span aria-hidden="true">→</span>';
  }
});
